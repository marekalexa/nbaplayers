package com.example.nbaplayers.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.nbaplayers.data.local.dao.PlayerDao
import com.example.nbaplayers.data.local.dao.PlayerRemoteKeysDao
import com.example.nbaplayers.data.local.dao.TeamDao
import com.example.nbaplayers.data.local.entity.PlayerEntity
import com.example.nbaplayers.data.local.entity.PlayerRemoteKeyEntity
import com.example.nbaplayers.data.local.entity.TeamEntity

/**
 * Main database class for the NBA Players application.
 * This Room database manages the local storage of NBA teams and players data.
 *
 * The database includes:
 * - Teams table
 * - Players table
 * - Player remote keys table for pagination
 *
 * @property teamDao Data Access Object for teams
 * @property playerDao Data Access Object for players
 * @property playerRemoteKeysDao Data Access Object for player remote keys
 */
@Database(
    entities = [
        TeamEntity::class,
        PlayerEntity::class,
        PlayerRemoteKeyEntity::class
    ],
    version = 1,
    exportSchema = false
)
abstract class AppDb : RoomDatabase() {
    abstract fun teamDao(): TeamDao
    abstract fun playerDao(): PlayerDao
    abstract fun playerRemoteKeysDao(): PlayerRemoteKeysDao
}
