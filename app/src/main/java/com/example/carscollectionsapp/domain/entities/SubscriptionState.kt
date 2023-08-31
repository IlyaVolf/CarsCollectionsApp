package com.example.carscollectionsapp.domain.entities

sealed class SubscriptionState {

    data class UnsubscribedState(
        val carWatchCount: Int,
        val carUploadCount: Int
    ) : SubscriptionState() {
        companion object {

            fun newInstance(): UnsubscribedState = UnsubscribedState(
                CAR_WATCH_DEFAULT,
                CAR_UPLOAD_DEFAULT
            )

        }
    }

    data object SubscribedState : SubscriptionState()

    companion object {
        const val UNLIMITED = -1
        const val CAR_WATCH_DEFAULT = 3
        const val CAR_UPLOAD_DEFAULT = 2
    }

}