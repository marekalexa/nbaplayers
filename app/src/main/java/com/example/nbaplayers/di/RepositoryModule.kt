package com.example.nbaplayers.di

import com.example.nbaplayers.data.repository.PlayersRepositoryImpl
import com.example.nbaplayers.data.repository.TeamsRepositoryImpl
import com.example.nbaplayers.domain.repository.PlayersRepository
import com.example.nbaplayers.domain.repository.TeamsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoryModule {

    @Binds
    abstract fun bindPlayersRepository(
        impl: PlayersRepositoryImpl
    ): PlayersRepository

    @Binds
    abstract fun bindTeamsRepository(
        impl: TeamsRepositoryImpl
    ): TeamsRepository
}