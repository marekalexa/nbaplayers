package com.example.nbaplayers.domain.usecase

import androidx.paging.PagingData
import com.example.nbaplayers.domain.model.Player
import com.example.nbaplayers.domain.repository.PlayersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for fetching NBA players with pagination support.
 * This encapsulates the business logic for retrieving players data.
 */
class GetPlayersUseCase @Inject constructor(
    private val repository: PlayersRepository
) {
    /**
     * Returns a Flow of paged player data.
     * The data is automatically cached and managed by the repository.
     *
     * @return Flow of paged player data
     */
    operator fun invoke(): Flow<PagingData<Player>> = repository.playersFlow()
} 