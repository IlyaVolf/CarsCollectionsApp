package com.example.carscollectionsapp.data.room.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(
    tableName = "cars"
)
data class CarDbEntity(
    @PrimaryKey(autoGenerate = true) val id: Long,
    val name: String,
    val photo: String?,
    val year: Int,
    val engineCapacity: Float,
    val dateAdded: Long
)