package com.example.nbaplayers.ui.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import androidx.paging.map
import com.example.nbaplayers.domain.repository.PlayersRepository
import com.example.nbaplayers.ui.model.PlayerUiModel
import com.example.nbaplayers.ui.model.PlayersScreenState
import com.example.nbaplayers.ui.model.toUiModel
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class PlayersViewModel @Inject constructor(
    repo: PlayersRepository
) : ViewModel() {

    /** Flow<PagingData<PlayerUiModel>> coming from the repo, already mapped
     *  from domain → UI models. */
    private val pagingFlow: Flow<PagingData<PlayerUiModel>> =
        repo.playersFlow()
            .map { paging ->
                paging.map { player ->
                    player.toUiModel()
                }
            }.cachedIn(viewModelScope)

    /** Exposed to the UI.  It wraps the *flow*, not a single page. */
    val screenState: StateFlow<PlayersScreenState> =
        MutableStateFlow(
            PlayersScreenState(players = pagingFlow)
        )
}

