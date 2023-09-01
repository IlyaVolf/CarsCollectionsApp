package com.example.carscollectionsapp.di

import com.example.carscollectionsapp.data.repositories.FakeSubscriptionsRepository
import com.example.carscollectionsapp.data.repositories.RoomCarsRepository
import com.example.carscollectionsapp.domain.CarsRepository
import com.example.carscollectionsapp.domain.SubscriptionsRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class RepositoriesModule {

    @Binds
    @Singleton
    abstract fun bindCarsRepository(
        carsRepository: RoomCarsRepository
    ): CarsRepository

    @Binds
    @Singleton
    abstract fun bindSubscriptionsRepository(
        subscriptionsRepository: FakeSubscriptionsRepository
    ): SubscriptionsRepository

}