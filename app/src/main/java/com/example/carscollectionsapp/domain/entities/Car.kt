package com.example.carscollectionsapp.domain.entities
data class Car(
    val id: Long,
    val name: String,
    val photo: String?,
    val year: Int,
    val engineCapacity: Float,
    val dateAdded: Long
)