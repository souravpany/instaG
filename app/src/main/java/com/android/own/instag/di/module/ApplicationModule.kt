package com.android.own.instag.di.module

import android.app.Application
import android.content.Context
import android.content.SharedPreferences
import com.android.own.instag.BuildConfig
import com.android.own.instag.InStaGApplication
import com.android.own.instag.data.remote.NetworkService
import com.android.own.instag.data.remote.Networking
import com.android.own.instag.di.ApplicationContext
import com.android.own.instag.di.TempDirectory
import com.android.own.instag.utils.common.FileUtils
import com.android.own.instag.utils.network.NetworkHelper
import com.android.own.instag.utils.rx.RxSchedulerProvider
import com.android.own.instag.utils.rx.SchedulerProvider
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Singleton

@Module
class ApplicationModule(private val application: InStaGApplication) {

    @Singleton
    @Provides
    fun provideApplication(): Application = application

    @Provides
    @Singleton
    @ApplicationContext
    fun provideContext(): Context = application


    /**
     * Since this function do not have @Singleton then each time CompositeDisposable is injected
     * then a new instance of CompositeDisposable will be provided
     */
    @Provides
    fun provideCompositeDisposable(): CompositeDisposable = CompositeDisposable()

    @Provides
    fun provideSchedulerProvider(): SchedulerProvider = RxSchedulerProvider()

    @Provides
    @Singleton
    fun provideSharedPreferences(): SharedPreferences =
        application.getSharedPreferences("bootcamp-instagram-project-prefs", Context.MODE_PRIVATE)

    @Provides
    @Singleton
    @TempDirectory
    fun provideTempDirectory() = FileUtils.getDirectory(application, "temp")


    @Provides
    @Singleton
    fun provideNetworkService(): NetworkService =
        Networking.create(
            BuildConfig.API_KEY,
            BuildConfig.BASE_URL,
            application.cacheDir,
            10 * 1024 * 1024 // 10MB
        )

    @Singleton
    @Provides
    fun provideNetworkHelper(): NetworkHelper = NetworkHelper(application)

}
