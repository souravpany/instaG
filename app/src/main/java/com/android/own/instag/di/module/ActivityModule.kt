package com.android.own.instag.di.module

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.own.instag.data.repository.RegisterUserRepository
import com.android.own.instag.data.repository.UserRepository
import com.android.own.instag.ui.base.BaseActivity
import com.android.own.instag.ui.login.LoginScreenViewModel
import com.android.own.instag.ui.main.MainScreenViewModel
import com.android.own.instag.ui.main.MainSharedViewModel
import com.android.own.instag.ui.register.RegisterScreenViewModel
import com.android.own.instag.ui.splash.SplashScreenViewModel
import com.android.own.instag.utils.ViewModelProviderFactory
import com.android.own.instag.utils.network.NetworkHelper
import com.android.own.instag.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable

/**
 * Kotlin Generics Reference: https://kotlinlang.org/docs/reference/generics.html
 * Basically it means that we can pass any class that extends BaseActivity which take
 * BaseViewModel subclass as parameter
 */

@Suppress("DEPRECATION")
@Module
class ActivityModule(private val activity: BaseActivity<*>) {

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(activity)


    @Provides
    fun provideSplashScreenViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper, userRepository: UserRepository
    ): SplashScreenViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(SplashScreenViewModel::class) {
            SplashScreenViewModel(
                schedulerProvider,
                networkHelper,
                compositeDisposable,
                userRepository
            )
        }).get(SplashScreenViewModel::class.java)


    @Provides
    fun provideLoginViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper, userRepository: UserRepository
    ): LoginScreenViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(LoginScreenViewModel::class) {
            LoginScreenViewModel(
                schedulerProvider,
                networkHelper,
                compositeDisposable,
                userRepository
            )
        }).get(LoginScreenViewModel::class.java)

    @Provides
    fun provideRegisterViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper, registerUserRepository: RegisterUserRepository
    ): RegisterScreenViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(RegisterScreenViewModel::class) {
            RegisterScreenViewModel(
                schedulerProvider,
                networkHelper,
                compositeDisposable,
                registerUserRepository
            )
        }).get(RegisterScreenViewModel::class.java)


    @Provides
    fun provideMainScreenViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): MainScreenViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(MainScreenViewModel::class) {
            MainScreenViewModel(
                schedulerProvider,
                compositeDisposable,
                networkHelper
            )
        }).get(MainScreenViewModel::class.java)


    @Provides
    fun provideMainSharedViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): MainSharedViewModel = ViewModelProviders.of(
        activity, ViewModelProviderFactory(MainSharedViewModel::class) {
            MainSharedViewModel(schedulerProvider, compositeDisposable, networkHelper)
        }).get(MainSharedViewModel::class.java)

}