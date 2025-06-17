@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)

package com.example.nbaplayers.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingSource
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.nbaplayers.data.local.AppDb
import com.example.nbaplayers.data.local.relation.PlayerWithTeam
import com.example.nbaplayers.data.remote.BalldontlieApi
import com.example.nbaplayers.data.remote.dto.PlayerDto
import com.example.nbaplayers.data.remote.dto.PlayersResponseDto
import com.example.nbaplayers.data.remote.dto.ResponseMetadataDto
import com.example.nbaplayers.data.remote.dto.TeamDto
import com.example.nbaplayers.data.remote.dto.toLocal
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    sdk = [34]
)
@OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)
class PlayersRemoteMediatorTest {

    private lateinit var db: AppDb
    private lateinit var api: FakeApi
    private val dispatcher = UnconfinedTestDispatcher()

    private fun makePage(startId: Int) =
        (startId until startId + 35).map { id ->
            val team = TeamDto(
                id = id,
                abbreviation = "A$id",
                city = "City$id",
                name = "Team$id",
                fullName = "City$id Team$id",
                conference = "C$id", division = "D$id"
            )
            PlayerDto(
                id = id,
                firstName = "F$id",
                lastName = "L$id",
                height = null,
                weight = null,
                position = "P$id",
                team = team
            )
        }

    @Before
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppDb::class.java
        ).allowMainThreadQueries().build()
        api = FakeApi(
            mapOf(
                null to PlayersResponseDto(makePage(0), ResponseMetadataDto(35, 35)),
                35 to PlayersResponseDto(makePage(35), ResponseMetadataDto(70, 35)),
                70 to PlayersResponseDto(makePage(70), ResponseMetadataDto(null, 35))
            )
        )
    }

    @After
    fun tearDown() {
        db.close()
        Dispatchers.resetMain()
    }

    private fun emptyState() = PagingState<Int, PlayerWithTeam>(
        pages = emptyList(),
        anchorPosition = null,
        config = PagingConfig(pageSize = 35),
        leadingPlaceholderCount = 0
    )

    private fun mediator() = PlayersRemoteMediator(api, db)
    private suspend fun count() = db.playerDao().ids().size

    @Test
    fun `refresh inserts first page`() = runTest {
        val result = mediator().load(LoadType.REFRESH, emptyState())
        assertTrue(result is RemoteMediator.MediatorResult.Success && !result.endOfPaginationReached)
        assertEquals(35, count())
    }

    @Test
    fun `append until end`() = runTest {
        // seed first page
        mediator().load(LoadType.REFRESH, emptyState())

        val firstPage: PagingSource.LoadResult.Page<Int, PlayerWithTeam> =
            PagingSource.LoadResult.Page(
                data = makePage(0).map { PlayerWithTeam(it.toLocal(), it.team.toLocal()) },
                prevKey = null, nextKey = null
            )
        val secondPage: PagingSource.LoadResult.Page<Int, PlayerWithTeam> =
            PagingSource.LoadResult.Page(
                data = makePage(35).map { PlayerWithTeam(it.toLocal(), it.team.toLocal()) },
                prevKey = null, nextKey = null
            )

        // APPEND #1 → should load items 35–69
        val stateOne = PagingState(
            pages = listOf(firstPage),
            anchorPosition = null,
            config = PagingConfig(pageSize = 35),
            leadingPlaceholderCount = 0
        )
        val result1 = mediator().load(LoadType.APPEND, stateOne)
        assertTrue(result1 is RemoteMediator.MediatorResult.Success && !result1.endOfPaginationReached)
        assertEquals(70, count())

        // APPEND #2 → should load items 70–104 and signal end
        val stateTwo = PagingState(
            pages = listOf(firstPage, secondPage),
            anchorPosition = null,
            config = PagingConfig(pageSize = 35),
            leadingPlaceholderCount = 0
        )
        val result2 = mediator().load(LoadType.APPEND, stateTwo)
        assertTrue(result2 is RemoteMediator.MediatorResult.Success && result2.endOfPaginationReached)
        assertEquals(105, count())
    }

    @Test
    fun `prepend is ignored`() = runTest {
        val result = mediator().load(LoadType.PREPEND, emptyState())
        assertTrue(result is RemoteMediator.MediatorResult.Success && result.endOfPaginationReached)
        assertEquals(0, count())
    }

    @Test
    fun `network error is propagated`() = runTest {
        api.throwNext = true
        val result = mediator().load(LoadType.REFRESH, emptyState())
        assertTrue(result is RemoteMediator.MediatorResult.Error)
        assertEquals(0, count())
    }
}

private class FakeApi(
    private val pages: Map<Int?, PlayersResponseDto>,
) : BalldontlieApi {

    var throwNext = false

    override suspend fun getPlayers(cursor: Int?, perPage: Int): PlayersResponseDto {
        if (throwNext) error("boom!")
        return pages[cursor] ?: error("unexpected cursor $cursor")
    }
}

