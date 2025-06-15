package com.example.nbaplayers.di

import com.example.nbaplayers.data.repository.PlayersRepositoryImpl
import com.example.nbaplayers.domain.repository.PlayersRepository
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
}