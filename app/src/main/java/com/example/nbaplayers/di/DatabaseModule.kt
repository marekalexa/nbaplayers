package com.example.nbaplayers.di

import android.content.Context
import androidx.room.Room
import com.example.nbaplayers.data.local.AppDb
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDb(
        @ApplicationContext ctx: Context
    ): AppDb =
        Room.databaseBuilder(ctx, AppDb::class.java, "nba_players.db")
            .fallbackToDestructiveMigration(true)
            .build()
}
