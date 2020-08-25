package com.android.own.instag.ui.main

import androidx.lifecycle.MutableLiveData
import com.android.own.instag.ui.base.BaseViewModel
import com.android.own.instag.utils.common.Event
import com.android.own.instag.utils.network.NetworkHelper
import com.android.own.instag.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class MainScreenViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    val profileNavigation = MutableLiveData<Event<Boolean>>()
    val homeNavigation = MutableLiveData<Event<Boolean>>()
    val photoNavigation = MutableLiveData<Event<Boolean>>()


    override fun onCreate() {
        homeNavigation.postValue(Event(true))
    }

    fun onProfileSelected() {
        profileNavigation.postValue(Event(true))
    }

    fun onHomeSelected() {
        homeNavigation.postValue(Event(true))
    }

    fun onPhotoSelected() {
        photoNavigation.postValue(Event(true))
    }
}