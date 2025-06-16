package com.example.nbaplayers.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nbaplayers.data.local.dao.PlayerDao
import com.example.nbaplayers.data.local.dao.PlayerRemoteKeysDao
import com.example.nbaplayers.data.local.dao.TeamDao
import com.example.nbaplayers.data.local.entity.PlayerEntity
import com.example.nbaplayers.data.local.entity.PlayerRemoteKeyEntity
import com.example.nbaplayers.data.local.entity.TeamEntity

@Database(entities = [PlayerEntity::class, TeamEntity::class,
    PlayerRemoteKeyEntity::class], version = 1)
abstract class AppDb : RoomDatabase() {
    abstract fun playerDao(): PlayerDao
    abstract fun teamDao(): TeamDao
    abstract fun playerRemoteKeysDao(): PlayerRemoteKeysDao
}
