package com.android.own.instag.ui.splash

import android.content.Intent
import android.os.Bundle
import androidx.lifecycle.Observer
import com.android.own.instag.R
import com.android.own.instag.di.component.ActivityComponent
import com.android.own.instag.ui.base.BaseActivity
import com.android.own.instag.ui.login.LoginScreenActivity
import com.android.own.instag.ui.main.MainScreenActivity
import com.android.own.instag.utils.common.Event

class SplashScreenActivity : BaseActivity<SplashScreenViewModel>() {


    override fun provideLayoutId(): Int = R.layout.activity_splash_screen

    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)
    }

    override fun setupView(savedInstanceState: Bundle?) {
    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.launchMain.observe(this, Observer<Event<Map<String, String>>> {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, MainScreenActivity::class.java))
                finish()
            }
        })

        viewModel.launchLoginActivity.observe(this, Observer<Event<Map<String, String>>> {
            it.getIfNotHandled()?.run {
                startActivity(Intent(applicationContext, LoginScreenActivity::class.java))
                finish()
            }
        })
    }
}
