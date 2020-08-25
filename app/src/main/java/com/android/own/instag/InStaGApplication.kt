package com.android.own.instag

import android.app.Application
import com.android.own.instag.di.component.ApplicationComponent
import com.android.own.instag.di.component.DaggerApplicationComponent
import com.android.own.instag.di.module.ApplicationModule

class InStaGApplication : Application() {


    lateinit var applicationComponent: ApplicationComponent


    override fun onCreate() {
        super.onCreate()
        injectDependencies()

    }


    private fun injectDependencies() {
        applicationComponent = DaggerApplicationComponent
            .builder()
            .applicationModule(ApplicationModule(this))
            .build()
        applicationComponent.inject(this)
    }

}
