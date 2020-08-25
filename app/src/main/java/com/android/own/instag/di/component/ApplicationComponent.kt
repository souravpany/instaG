package com.android.own.instag.di.component

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.android.own.instag.InStaGApplication
import com.android.own.instag.data.remote.NetworkService
import com.android.own.instag.data.repository.RegisterUserRepository
import com.android.own.instag.data.repository.UserPostRepository
import com.android.own.instag.data.repository.UserRepository
import com.android.own.instag.di.ApplicationContext
import com.android.own.instag.di.TempDirectory
import com.android.own.instag.di.module.ApplicationModule
import com.android.own.instag.utils.network.NetworkHelper
import com.android.own.instag.utils.rx.SchedulerProvider
import dagger.Component
import io.reactivex.disposables.CompositeDisposable
import java.io.File
import javax.inject.Singleton

@Singleton
@Component(modules = [ApplicationModule::class])
interface ApplicationComponent {


    fun inject(app: InStaGApplication)

    fun getApplication(): Application

    @ApplicationContext
    fun getContext(): Context

    fun getCompositeDisposable(): CompositeDisposable

    fun getSchedulerProvider(): SchedulerProvider


    fun getSharedPreferences(): SharedPreferences

    fun getNetworkHelper(): NetworkHelper

    fun getNetworkService(): NetworkService

    fun getUserRepository(): UserRepository

    fun getRegisterUserRepository(): RegisterUserRepository

    fun getUserPostRepository(): UserPostRepository


    @TempDirectory
    fun getTempDirectory(): File

}
