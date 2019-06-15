package com.tasks.accenturetask.base.presentation.model

import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.Observer
import com.tasks.accenturetask.base.domain.exception.AccentureException
import com.tasks.accenturetask.base.presentation.model.SingleLiveEvent

class ObservableResource<T> : SingleLiveEvent<T>() {

    val error: SingleLiveEvent<AccentureException> = ErrorLiveData()
    val loading: SingleLiveEvent<Boolean> = SingleLiveEvent()

    fun observe(
        owner: LifecycleOwner, successObserver: Observer<T>,
        loadingObserver: Observer<Boolean>? = null,
        commonErrorObserver: Observer<AccentureException>,
        httpErrorConsumer: Observer<AccentureException>? = null,
        networkErrorConsumer: Observer<AccentureException>? = null,
        unExpectedErrorConsumer: Observer<AccentureException>? = null,
        serverDownErrorConsumer: Observer<AccentureException>? = null,
        timeOutErrorConsumer: Observer<AccentureException>? = null,
        unAuthorizedErrorConsumer: Observer<AccentureException>? = null
    ) {
        super.observe(owner, successObserver)
        loadingObserver?.let { loading.observe(owner, it) }
        (error as ErrorLiveData).observe(
            owner, commonErrorObserver, httpErrorConsumer, networkErrorConsumer, unExpectedErrorConsumer,
            serverDownErrorConsumer, timeOutErrorConsumer, unAuthorizedErrorConsumer
        )
    }


    class ErrorLiveData : SingleLiveEvent<AccentureException>() {
        private var ownerRef: LifecycleOwner? = null
        private var httpErrorConsumer: Observer<AccentureException>? = null
        private var networkErrorConsumer: Observer<AccentureException>? = null
        private var unExpectedErrorConsumer: Observer<AccentureException>? = null
        private var commonErrorConsumer: Observer<AccentureException>? = null

        private var serverDownErrorConsumer: Observer<AccentureException>? = null
        private var timeOutErrorConsumer: Observer<AccentureException>? = null
        private var unAuthorizedErrorConsumer: Observer<AccentureException>? = null

        override fun setValue(t: AccentureException?) {
            ownerRef?.also {
                removeObservers(it)
                t?.let { vale -> addProperObserver(vale) }
                super.setValue(t)
            }

        }

        override fun postValue(value: AccentureException) {
            ownerRef?.also {
                removeObservers(it)
                addProperObserver(value)
                super.postValue(value)
            }

        }

        private fun addProperObserver(value: AccentureException) {
            when (value.kind) {
                AccentureException.Kind.NETWORK -> networkErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)
                AccentureException.Kind.HTTP -> httpErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)
                AccentureException.Kind.UNEXPECTED -> unExpectedErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                AccentureException.Kind.SERVER_DOWN -> serverDownErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                AccentureException.Kind.TIME_OUT -> timeOutErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                AccentureException.Kind.UNAUTHORIZED -> unAuthorizedErrorConsumer?.let { observe(ownerRef!!, it) }
                    ?: observe(ownerRef!!, commonErrorConsumer!!)

                else -> {
                }
            }
        }


        fun observe(
            owner: LifecycleOwner, commonErrorConsumer: Observer<AccentureException>,
            httpErrorConsumer: Observer<AccentureException>? = null,
            networkErrorConsumer: Observer<AccentureException>? = null,
            unExpectedErrorConsumer: Observer<AccentureException>? = null,

            serverDownErrorConsumer: Observer<AccentureException>? = null,
            timeOutErrorConsumer: Observer<AccentureException>? = null,
            unAuthorizedErrorConsumer: Observer<AccentureException>? = null
        ) {
            super.observe(owner, commonErrorConsumer)
            this.ownerRef = owner
            this.commonErrorConsumer = commonErrorConsumer
            this.httpErrorConsumer = httpErrorConsumer
            this.networkErrorConsumer = networkErrorConsumer
            this.unExpectedErrorConsumer = unExpectedErrorConsumer
            this.serverDownErrorConsumer = serverDownErrorConsumer
            this.timeOutErrorConsumer = timeOutErrorConsumer
            this.unAuthorizedErrorConsumer = unAuthorizedErrorConsumer
        }
    }
}