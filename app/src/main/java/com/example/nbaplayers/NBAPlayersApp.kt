package com.example.nbaplayers

import android.app.Application
import dagger.hilt.android.HiltAndroidApp

/**
 * Application class annotated for Dagger Hilt setup.
 *
 * Required for dependency injection across the app.
 */
@HiltAndroidApp
class NBAPlayersApp : Application()