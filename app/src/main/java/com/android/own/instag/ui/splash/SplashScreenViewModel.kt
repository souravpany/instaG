package com.android.own.instag.ui.splash

import android.os.Handler
import androidx.lifecycle.MutableLiveData
import com.android.own.instag.data.repository.UserRepository
import com.android.own.instag.ui.base.BaseViewModel
import com.android.own.instag.utils.common.Event
import com.android.own.instag.utils.network.NetworkHelper
import com.android.own.instag.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable

class SplashScreenViewModel(
    schedulerProvider: SchedulerProvider,
    networkHelper: NetworkHelper,
    compositeDisposable: CompositeDisposable,
    val userRepository: UserRepository
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {

    private val splashTimeOut: Long = 3000// 3 sec
    val launchLoginActivity: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()
    val launchMain: MutableLiveData<Event<Map<String, String>>> = MutableLiveData()


    override fun onCreate() {

        Handler().postDelayed({
            if (userRepository.getCurrentUser() != null)
                launchMain.postValue(Event(emptyMap()))
            else
                launchLoginActivity.postValue(Event(emptyMap()))
        }, splashTimeOut)
    }
}