package com.example.carscollectionsapp.di

import com.example.carscollectionsapp.data.repositories.FakeSubscriptionsRepository
import com.example.carscollectionsapp.data.repositories.RoomCarsRepository
import com.example.carscollectionsapp.domain.CarsRepository
import com.example.carscollectionsapp.domain.SubscriptionsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    abstract fun bindCarsRepository(
        carsRepository: RoomCarsRepository
    ): CarsRepository

    @Binds
    abstract fun bindSubscriptionsRepository(
        subscriptionsRepository: FakeSubscriptionsRepository
    ): SubscriptionsRepository

}