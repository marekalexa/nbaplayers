package com.example.nbaplayers.domain.usecase

import androidx.paging.PagingData
import com.example.nbaplayers.TestData
import com.example.nbaplayers.domain.model.Player
import com.example.nbaplayers.domain.repository.PlayersRepository
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import kotlin.test.Test
import kotlin.test.assertEquals

class GetPlayersUseCaseTest {

    @Test
    fun `when invoked, returns paging data from repository`() = runTest {
        // Given
        val repository = mockk<PlayersRepository>()
        val useCase = GetPlayersUseCase(repository)
        val expectedPlayers = listOf(TestData.player1, TestData.player2)
        val expectedPagingData = PagingData.from(expectedPlayers)
        
        coEvery { repository.playersFlow() } returns flowOf(expectedPagingData)

        // When
        val result = useCase().first()

        // Then
        assertEquals(expectedPagingData, result)
    }

    @Test
    fun `when repository returns empty data, returns empty paging data`() = runTest {
        // Given
        val repository = mockk<PlayersRepository>()
        val useCase = GetPlayersUseCase(repository)
        val expectedPagingData = PagingData.empty<Player>()
        
        coEvery { repository.playersFlow() } returns flowOf(expectedPagingData)

        // When
        val result = useCase().first()

        // Then
        assertEquals(expectedPagingData, result)
    }
} 