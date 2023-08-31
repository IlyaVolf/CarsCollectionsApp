package com.example.carscollectionsapp.data.repositories

import android.content.Context
import android.content.SharedPreferences
import android.provider.Settings.Global.putString
import androidx.core.content.edit
import com.example.carscollectionsapp.di.IoDispatcher
import com.example.carscollectionsapp.domain.SubscriptionsRepository
import com.example.carscollectionsapp.domain.entities.SubscriptionState
import com.example.carscollectionsapp.domain.entities.SubscriptionState.Companion.CAR_UPLOAD_DEFAULT
import com.example.carscollectionsapp.domain.entities.SubscriptionState.Companion.CAR_WATCH_DEFAULT
import com.example.carscollectionsapp.domain.entities.SubscriptionState.Companion.UNLIMITED
import com.example.carscollectionsapp.domain.subscription_manager.BillingManager
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

class FakeSubscriptionsRepository @Inject constructor(
    @ApplicationContext private val context: Context,
    private val billingManager: BillingManager,
) : SubscriptionsRepository, SharedPreferences.OnSharedPreferenceChangeListener {

    private val preferences = context.getSharedPreferences(
        PREFERENCES_NAME, Context.MODE_PRIVATE
    )
    override val subscriptionState =
        MutableStateFlow<SubscriptionState>(SubscriptionState.UnsubscribedState.newInstance())

    init {
        preferences.registerOnSharedPreferenceChangeListener(this)
    }

    override fun purchase() {
        preferences.edit {
            putBoolean(PREF_IS_SUBSCRIBED, true)
            putInt(PREF_CAR_WATCH, UNLIMITED)
            putInt(PREF_CAR_UPLOAD, UNLIMITED)
        }
    }

    override fun reset() {
        preferences.edit {
            putBoolean(PREF_IS_SUBSCRIBED, true)
            putInt(PREF_CAR_WATCH, CAR_WATCH_DEFAULT)
            putInt(PREF_CAR_UPLOAD, CAR_UPLOAD_DEFAULT)
        }
    }

    fun getSubscriptionState(): SubscriptionState {
        val isSubscribed = preferences.getBoolean(PREF_IS_SUBSCRIBED, false)
        val carWatchCount = preferences.getInt(PREF_CAR_WATCH, CAR_WATCH_DEFAULT)
        val carUploadCount = preferences.getInt(PREF_CAR_UPLOAD, CAR_UPLOAD_DEFAULT)

        return if (isSubscribed) {
            SubscriptionState.SubscribedState
        } else {
            SubscriptionState.UnsubscribedState(
                carWatchCount = carWatchCount,
                carUploadCount = carUploadCount
            )
        }
    }

    private companion object {
        const val PREFERENCES_NAME = "preferences"
        const val PREF_IS_SUBSCRIBED = "is_subscribed"
        const val PREF_CAR_WATCH = "car_watch"
        const val PREF_CAR_UPLOAD = "car_upload"
    }

    override fun onSharedPreferenceChanged(p0: SharedPreferences?, p1: String?) {
        subscriptionState.value = getSubscriptionState()
    }


}