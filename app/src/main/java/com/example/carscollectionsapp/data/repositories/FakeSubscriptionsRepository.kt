package com.example.carscollectionsapp.data.repositories

import android.content.Context
import android.content.SharedPreferences
import android.util.Log
import androidx.core.content.edit
import com.example.carscollectionsapp.di.IoDispatcher
import com.example.carscollectionsapp.domain.SubscriptionsRepository
import com.example.carscollectionsapp.domain.entities.SubscriptionDetails
import com.example.carscollectionsapp.domain.entities.SubscriptionState
import com.example.carscollectionsapp.domain.entities.SubscriptionState.Companion.CAR_UPLOAD_DEFAULT
import com.example.carscollectionsapp.domain.entities.SubscriptionState.Companion.CAR_WATCH_DEFAULT
import com.example.carscollectionsapp.domain.entities.SubscriptionState.Companion.UNLIMITED
import com.example.carscollectionsapp.domain.subscription_manager.BillingManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject
import kotlin.time.Duration.Companion.days

class FakeSubscriptionsRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val billingManager: BillingManager,
    @IoDispatcher private val dispatcher: CoroutineDispatcher
) : SubscriptionsRepository, SharedPreferences.OnSharedPreferenceChangeListener {

    private val preferences = context.getSharedPreferences(
        PREFERENCES_NAME, Context.MODE_PRIVATE
    )

    private val _subscriptionStateFlow =
        MutableStateFlow(getSubscriptionStateFromSharedPreferences())
    override val subscriptionStateFlow = _subscriptionStateFlow.asStateFlow()

    init {
        preferences.registerOnSharedPreferenceChangeListener(this)
        getSubscriptionStateFromSharedPreferences()
    }

    override suspend fun getSubscriptionDetails(): SubscriptionDetails = SubscriptionDetails(
        name = "Limits off",
        description = "Turns all limits off with this subscription",
        price = 1000f,
        subscriptionPeriod = 3.days
    )

    override suspend fun countAsCarDetailsOpened() = withContext(dispatcher) {
        when (val subscriptionState = _subscriptionStateFlow.value) {
            is SubscriptionState.SubscribedState -> {
                return@withContext
            }

            is SubscriptionState.UnsubscribedState -> {
                val newSubscriptionState = subscriptionState.copy(
                    carWatchCount = subscriptionState.carWatchCount - 1
                )
                Log.d("ABCD", "countAsCarDetailsOpened $newSubscriptionState")
                setSubscriptionState(newSubscriptionState)
                return@withContext
            }
        }
    }

    override suspend fun countAsCarAddOpened() = withContext(dispatcher) {
        when (val subscriptionState = _subscriptionStateFlow.value) {
            is SubscriptionState.SubscribedState -> {
                return@withContext
            }

            is SubscriptionState.UnsubscribedState -> {
                val newSubscriptionState = subscriptionState.copy(
                    carUploadCount = subscriptionState.carUploadCount - 1
                )
                Log.d("ABCD", "countAsCarDetailsOpened $newSubscriptionState")
                setSubscriptionState(newSubscriptionState)
                return@withContext
            }
        }
    }

    override suspend fun purchase() = withContext(dispatcher) {
        preferences.edit {
            putBoolean(PREF_IS_SUBSCRIBED, true)
            putInt(PREF_CAR_WATCH, UNLIMITED)
            putInt(PREF_CAR_UPLOAD, UNLIMITED)
        }
    }

    private suspend fun setSubscriptionState(subscriptionState: SubscriptionState) =
        withContext(dispatcher) {
            when (subscriptionState) {
                is SubscriptionState.UnsubscribedState -> {
                    preferences.edit {
                        putBoolean(PREF_IS_SUBSCRIBED, false)
                        putInt(PREF_CAR_WATCH, subscriptionState.carWatchCount)
                        putInt(PREF_CAR_UPLOAD, subscriptionState.carUploadCount)
                    }
                }

                is SubscriptionState.SubscribedState -> {
                    preferences.edit {
                        putBoolean(PREF_IS_SUBSCRIBED, true)
                        putInt(PREF_CAR_WATCH, UNLIMITED)
                        putInt(PREF_CAR_UPLOAD, UNLIMITED)
                    }
                }
            }
        }

    override suspend fun reset() = withContext(dispatcher) {
        preferences.edit {
            putBoolean(PREF_IS_SUBSCRIBED, false)
            putInt(PREF_CAR_WATCH, CAR_WATCH_DEFAULT)
            putInt(PREF_CAR_UPLOAD, CAR_UPLOAD_DEFAULT)
        }
    }

    private fun getSubscriptionStateFromSharedPreferences(): SubscriptionState {
        val isSubscribed = preferences.getBoolean(PREF_IS_SUBSCRIBED, false)
        val carWatchCount = preferences.getInt(PREF_CAR_WATCH, CAR_WATCH_DEFAULT)
        val carUploadCount = preferences.getInt(PREF_CAR_UPLOAD, CAR_UPLOAD_DEFAULT)

        return if (isSubscribed) {
            SubscriptionState.SubscribedState
        } else {
            SubscriptionState.UnsubscribedState(
                carWatchCount = carWatchCount,
                carUploadCount = carUploadCount
            ).also {
                Log.d("ABCD", "getSubscriptionStateFromSharedPreferences $this")
            }
        }
    }

    private companion object {
        const val PREFERENCES_NAME = "preferences"
        const val PREF_IS_SUBSCRIBED = "is_subscribed"
        const val PREF_CAR_WATCH = "car_watch"
        const val PREF_CAR_UPLOAD = "car_upload"
    }

    override fun onSharedPreferenceChanged(sharedPreferences: SharedPreferences?, key: String?) {
        Log.d("ABCD", "onSharedPreferenceChanged")
        _subscriptionStateFlow.value = getSubscriptionStateFromSharedPreferences()
    }


}