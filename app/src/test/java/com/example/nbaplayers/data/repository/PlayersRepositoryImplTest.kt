package com.example.nbaplayers.data.repository

import androidx.paging.CombinedLoadStates
import androidx.paging.testing.ErrorRecovery
import androidx.paging.testing.LoadErrorHandler
import androidx.paging.testing.asSnapshot
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.example.nbaplayers.TestData
import com.example.nbaplayers.data.local.AppDb
import com.example.nbaplayers.data.remote.BalldontlieApi
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.test.runTest
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertTrue

@RunWith(RobolectricTestRunner::class)
@Config(
    manifest = Config.NONE,
    sdk = [34]
)
class PlayersRepositoryImplTest {

    private lateinit var db: AppDb
    private lateinit var repository: PlayersRepositoryImpl
    private val mockApi: BalldontlieApi = mockk()

    @BeforeTest
    fun setup() {
        db = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),
            AppDb::class.java
        ).allowMainThreadQueries().build()

        repository = PlayersRepositoryImpl(db, mockApi)
    }

    @AfterTest
    fun tearDown() {
        db.clearAllTables()
        db.close()
    }

    @Test
    fun `playersFlow emits correctly mapped player data`() = runTest {
        // Given
        val team = TestData.teamEntity1
        val player = TestData.playerEntity1
        coEvery { mockApi.getPlayers(any(), any()) } returns TestData.apiResponse1
        db.teamDao().upsertTeams(listOf(team))
        db.playerDao().upsertPlayers(listOf(player))

        // When
        val snapshot = repository.playersFlow().asSnapshot()

        // Then
        assertEquals(1, snapshot.size)
        assertEquals(player.id, snapshot.first().id)
        assertEquals(team.fullName, snapshot.first().team?.fullName)
    }

    @Test
    fun `teamPlayersFlow returns only players from specified team`() = runTest {
        // Given
        val team1 = TestData.teamEntity1
        val team2 = TestData.teamEntity2
        val player1 = TestData.playerEntity1.copy(teamId = team1.id)
        val player2 = TestData.playerEntity2.copy(teamId = team2.id)
        val player3 = TestData.playerEntity3.copy(teamId = team2.id)
        db.teamDao().upsertTeams(listOf(team1, team2))
        db.playerDao().upsertPlayers(listOf(player1, player2, player3))

        // When
        val team1Snapshot = repository.teamPlayersFlow(team1.id).asSnapshot()
        val team2Snapshot = repository.teamPlayersFlow(team2.id).asSnapshot()

        // Then
        assertEquals(1, team1Snapshot.size)
        assertTrue { team1Snapshot.all { it.team?.id == team1.id || it.team == null } }

        assertEquals(2, team2Snapshot.size)
        assertTrue { team2Snapshot.all { it.team?.id == team2.id || it.team == null } }
    }

    @Test
    fun `playersFlow completes when nextCursor is null`() = runTest {
        // Given
        val emptyResponse = TestData.apiResponse1.copy(
            data = emptyList(),
            meta = TestData.apiResponse1.meta.copy(nextCursor = null)
        )
        coEvery { mockApi.getPlayers(any(), any()) } returns emptyResponse

        // When
        val snapshot = repository.playersFlow().asSnapshot()

        // Then
        assertEquals(0, snapshot.size)
    }

    @Test
    fun `playersFlow handles api exception gracefully`() = runTest {
        // Given
        coEvery { mockApi.getPlayers(any(), any()) } returns TestData.apiResponse1
        val snapshot1 = repository.playersFlow().asSnapshot()
        assertEquals(1, snapshot1.size)

        // When
        coEvery { mockApi.getPlayers(any(), any()) } throws RuntimeException("Network error")
        var errorHandled = false

        val snapshot = repository.playersFlow().asSnapshot(
            onError = object : LoadErrorHandler {
                override fun onError(combinedLoadStates: CombinedLoadStates): ErrorRecovery {
                    errorHandled = true
                    return ErrorRecovery.RETRY
                }
            }
        )

        // Then
        assertTrue(errorHandled)
        assertEquals(1, snapshot.size)
    }
}
