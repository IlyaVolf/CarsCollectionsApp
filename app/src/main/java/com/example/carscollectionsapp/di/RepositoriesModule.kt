package com.example.carscollectionsapp.di

import com.example.carscollectionsapp.RoomCarsRepository
import com.example.carscollectionsapp.domain.CarsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindCocktailsRepository(
        carsRepository: RoomCarsRepository
    ): CarsRepository

}