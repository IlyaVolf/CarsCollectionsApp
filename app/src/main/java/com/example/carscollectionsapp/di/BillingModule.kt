package com.example.carscollectionsapp.di

import com.example.carscollectionsapp.domain.subscription_manager.BillingManager
import com.example.carscollectionsapp.domain.subscription_manager.FakeBillingManager
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class BillingModule {

    @Provides
    @Singleton
    fun provideBillingManager(): BillingManager {
        return FakeBillingManager()
    }

}