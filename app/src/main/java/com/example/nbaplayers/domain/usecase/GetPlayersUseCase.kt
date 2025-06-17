package com.example.nbaplayers.domain.usecase

import androidx.paging.PagingData
import com.example.nbaplayers.domain.model.Player
import com.example.nbaplayers.domain.repository.PlayersRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

/**
 * Use case for fetching NBA players with pagination support.
 * This encapsulates the business logic for retrieving player data from the repository.
 *
 * @property repository The player repository used to fetch data.
 */
class GetPlayersUseCase @Inject constructor(
    private val repository: PlayersRepository
) {
    /**
     * Executes the use case.
     *
     * @return Flow of paginated player data.
     */
    operator fun invoke(): Flow<PagingData<Player>> = repository.playersFlow()
}
