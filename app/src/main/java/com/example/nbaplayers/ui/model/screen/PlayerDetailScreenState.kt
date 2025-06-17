package com.example.nbaplayers.ui.model.screen

import com.example.nbaplayers.ui.model.PlayerDetailUiModel

/**
 * UI state for [PlayerDetailScreen].
 *
 * @param player Player details to be shown, or null while loading.
 */
data class PlayerDetailScreenState(val player: PlayerDetailUiModel? = null)
