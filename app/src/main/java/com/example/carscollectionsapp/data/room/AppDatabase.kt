package com.example.carscollectionsapp.data.room

import androidx.room.Database
import androidx.room.RoomDatabase
import com.example.carscollectionsapp.data.room.entities.CarDbEntity

@Database(
    version = 1,
    entities = [
        CarDbEntity::class,
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun getCarsDao(): CarsDao

}