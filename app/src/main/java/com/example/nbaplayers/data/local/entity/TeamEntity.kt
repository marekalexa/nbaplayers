package com.example.nbaplayers.data.local.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.example.nbaplayers.domain.model.Team

@Entity(tableName = "teams")
data class TeamEntity(
    @PrimaryKey val id: Int,
    val fullName: String,
    val abbreviation: String,
    val city: String,
    val conference: String,
    val division: String,
    val name: String
)

fun TeamEntity.toDomainModel() = Team(
    id = id,
    fullName = fullName,
    abbreviation = abbreviation,
    city = city,
    conference = conference,
    division = division,
    name = name
)