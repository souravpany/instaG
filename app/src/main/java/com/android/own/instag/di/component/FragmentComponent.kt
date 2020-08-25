package com.android.own.instag.di.component

import com.android.own.instag.di.FragmentScope
import com.android.own.instag.di.module.FragmentModule
import com.android.own.instag.ui.home.HomeFragment
import com.android.own.instag.ui.photo.PhotoFragment
import com.android.own.instag.ui.profile.ProfileFragment
import dagger.Component

@FragmentScope
@Component(
    dependencies = [ApplicationComponent::class],
    modules = [FragmentModule::class]
)
interface FragmentComponent {

    fun inject(fragment: HomeFragment)

    fun inject(fragment: PhotoFragment)

    fun inject(fragment: ProfileFragment)
}