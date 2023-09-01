package com.example.carscollectionsapp.di

import android.content.Context
import androidx.room.Room
import com.example.carscollectionsapp.data.room.AppDatabase
import com.example.carscollectionsapp.data.room.CarsDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DatabaseModule {

    @Provides
    @Singleton
    fun provideAppDatabase(@ApplicationContext context: Context): AppDatabase {
        return Room.databaseBuilder(context, AppDatabase::class.java, "database.db")
            .build()
    }

    @Provides
    @Singleton
    fun provideCarsDao(database: AppDatabase): CarsDao {
        return database.getCarsDao()
    }

}