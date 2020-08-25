package com.android.own.instag.di.component

import com.android.own.instag.di.ActivityScope
import com.android.own.instag.di.module.ActivityModule
import com.android.own.instag.ui.login.LoginScreenActivity
import com.android.own.instag.ui.main.MainScreenActivity
import com.android.own.instag.ui.register.RegisterScreenActivity
import com.android.own.instag.ui.splash.SplashScreenActivity
import dagger.Component

@ActivityScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [ActivityModule::class]
)
interface ActivityComponent {

    fun inject(activity: SplashScreenActivity)

    fun inject(activity: LoginScreenActivity)

    fun inject(activity: RegisterScreenActivity)

    fun inject(activity: MainScreenActivity)

}