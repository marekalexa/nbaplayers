package com.example.nbaplayers.ui.viewmodel

import androidx.paging.PagingData
import androidx.paging.testing.asSnapshot
import com.example.nbaplayers.TestData
import com.example.nbaplayers.domain.usecase.GetPlayersUseCase
import com.example.nbaplayers.ui.model.getPlayerHeadshot
import com.example.nbaplayers.ui.model.toUiModel
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlin.test.AfterTest
import kotlin.test.BeforeTest
import kotlin.test.Test
import kotlin.test.assertEquals


@OptIn(ExperimentalCoroutinesApi::class)
class PlayersViewModelTest {

    private lateinit var usecase: GetPlayersUseCase
    private val testDispatcher = StandardTestDispatcher()

    @BeforeTest
    fun setup() {
        Dispatchers.setMain(testDispatcher)
        usecase = mockk()
    }

    @AfterTest
    fun tearDown() {
        Dispatchers.resetMain()
    }

    private fun testViewModel(): PlayersListViewModel =
        object : PlayersListViewModel(usecase) {
            override val cachingEnabled = false
        }

    @Test
    fun `repository emits empty paging data, screen state contains empty UI data`() = runTest {
        coEvery { usecase() } returns flowOf(PagingData.empty())

        val viewModel = testViewModel()
        val state = viewModel.screenState.value
        val players = state.players.asSnapshot()

        assertEquals(0, players.size)
    }

    @Test
    fun `repository emits player data, screen state contains transformed UI models`() =
        runTest {
            val player = TestData.player1
            coEvery { usecase() } returns flowOf(PagingData.from(listOf(player)))

            val viewModel = testViewModel()
            val uiPlayer = viewModel.screenState.value.players.asSnapshot().first()

            assertEquals(player.id, uiPlayer.id)
            assertEquals("John Doe", uiPlayer.fullname)
            assertEquals("PG", uiPlayer.position)
            assertEquals("Lakers", uiPlayer.teamName)
            assertEquals(getPlayerHeadshot(player.id), uiPlayer.headshot)
        }

    @Test
    fun `repository emits multiple players, screen state contains all transformed UI models`() =
        runTest {
            val players = listOf(TestData.player1, TestData.player2)
            coEvery { usecase() } returns flowOf(PagingData.from(players))

            val viewModel = testViewModel()
            val actual = viewModel.screenState.value.players.asSnapshot()
            val expected = players.map { it.toUiModel() }

            assertEquals(expected, actual)
        }

    @Test
    fun `repository emits player without team, screen state contains UI model with null team name`() =
        runTest {
            val player = TestData.player3
            coEvery { usecase() } returns flowOf(PagingData.from(listOf(player)))

            val viewModel = testViewModel()
            val result = viewModel.screenState.value.players.asSnapshot()

            assertEquals(1, result.size)
            assertEquals(player.toUiModel(), result.first())
            assertEquals(null, result.first().teamName)
        }
}
