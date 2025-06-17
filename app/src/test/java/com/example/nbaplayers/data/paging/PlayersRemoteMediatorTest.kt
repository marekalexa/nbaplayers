@file:OptIn(ExperimentalCoroutinesApi::class, ExperimentalPagingApi::class)

package com.example.nbaplayers.data.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingConfig
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.nbaplayers.TestData
import com.example.nbaplayers.data.local.AppDb
import com.example.nbaplayers.data.local.relation.PlayerWithTeam
import com.example.nbaplayers.data.remote.BalldontlieApi
import com.example.nbaplayers.data.remote.dto.PaginationMetadataDto
import com.example.nbaplayers.data.remote.dto.PlayerDto
import com.example.nbaplayers.data.remote.dto.PlayersResponseDto
import com.example.nbaplayers.data.remote.dto.TeamDto
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.AfterTest
import kotlin.test.BeforeTest

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
        (startId until startId + TestData.pageSize).map { id ->
            val team = TeamDto(
                id = id,
                abbreviation = "A$id",
                city = "City$id",
                name = "Team$id",
                fullName = "City$id Team$id",
                conference = "C$id",
                division = "D$id"
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

    @BeforeTest
    fun setUp() {
        Dispatchers.setMain(dispatcher)
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppDb::class.java
        ).build()
        api = FakeApi(
            mapOf(
                null to PlayersResponseDto(
                    makePage(0),
                    PaginationMetadataDto(TestData.pageSize, TestData.pageSize)
                ),
                TestData.pageSize to PlayersResponseDto(
                    makePage(TestData.pageSize),
                    PaginationMetadataDto(TestData.pageSize * 2, TestData.pageSize)
                ),
                TestData.pageSize * 2 to PlayersResponseDto(
                    makePage(TestData.pageSize * 2),
                    PaginationMetadataDto(null, TestData.pageSize)
                )
            )
        )
    }

    @AfterTest
    fun tearDown() {
        db.close()
        Dispatchers.resetMain()
    }

    private fun emptyState(): PagingState<Int, PlayerWithTeam> = PagingState(
        pages = emptyList(),
        anchorPosition = null,
        config = PagingConfig(pageSize = 35),
        leadingPlaceholderCount = 0
    )

    private fun mediator() = PlayersRemoteMediator(api, db)
    private suspend fun count() = db.playerDao().ids().size

    @Test
    fun `refresh inserts first page`() = runTest {
        // Given
        val mediator = mediator()

        // When
        val result = mediator.load(LoadType.REFRESH, emptyState())

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Success && !result.endOfPaginationReached)
        assertEquals(35, count())
    }

    @Test
    fun `append until end`() = runTest {
        // Given
        val mediator = mediator()
        mediator.load(LoadType.REFRESH, emptyState())

        // When: Append page 2
        val stateOne = PagingState<Int, PlayerWithTeam>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 35),
            leadingPlaceholderCount = 0
        )
        val result1 = mediator.load(LoadType.APPEND, stateOne)

        // Then
        assertTrue(result1 is RemoteMediator.MediatorResult.Success && !result1.endOfPaginationReached)
        assertEquals(70, count())

        // When: Append page 3
        val stateTwo = PagingState<Int, PlayerWithTeam>(
            pages = listOf(),
            anchorPosition = null,
            config = PagingConfig(pageSize = 35),
            leadingPlaceholderCount = 0
        )
        val result2 = mediator.load(LoadType.APPEND, stateTwo)

        // Then
        assertTrue(result2 is RemoteMediator.MediatorResult.Success && result2.endOfPaginationReached)
        assertEquals(105, count())
    }

    @Test
    fun `prepend is ignored`() = runTest {
        // Given
        val mediator = mediator()

        // When
        val result = mediator.load(LoadType.PREPEND, emptyState())

        // Then
        assertTrue(result is RemoteMediator.MediatorResult.Success && result.endOfPaginationReached)
        assertEquals(0, count())
    }

    @Test
    fun `network error is propagated`() = runTest {
        // Given
        api.throwNext = true
        val mediator = mediator()

        // When
        val result = mediator.load(LoadType.REFRESH, emptyState())

        // Then
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

