package com.android.own.instag.ui.main

import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import com.android.own.instag.R
import com.android.own.instag.di.component.ActivityComponent
import com.android.own.instag.ui.base.BaseActivity
import com.android.own.instag.ui.home.HomeFragment
import com.android.own.instag.ui.photo.PhotoFragment
import com.android.own.instag.ui.profile.ProfileFragment
import kotlinx.android.synthetic.main.activity_main_screen.*
import javax.inject.Inject

class MainScreenActivity : BaseActivity<MainScreenViewModel>() {

    private var activeFragment: Fragment? = null

    @Inject
    lateinit var mainSharedViewModel: MainSharedViewModel


    override fun provideLayoutId(): Int = R.layout.activity_main_screen


    override fun injectDependencies(activityComponent: ActivityComponent) {
        activityComponent.inject(this)

    }


    override fun setupView(savedInstanceState: Bundle?) {

        bottomNavigation.run {
            itemIconTintList = null
            setOnNavigationItemSelectedListener {
                when (it.itemId) {
                    R.id.itemHome -> {
                        viewModel.onHomeSelected()
                        true
                    }
                    R.id.itemAddPhotos -> {
                        viewModel.onPhotoSelected()
                        true
                    }
                    R.id.itemProfile -> {
                        viewModel.onProfileSelected()
                        true
                    }
                    else -> false
                }
            }
        }


    }

    override fun setupObservers() {
        super.setupObservers()

        viewModel.homeNavigation.observe(this, Observer {
            it.getIfNotHandled()?.run { showHome() }
        })

        viewModel.photoNavigation.observe(this, Observer {
            it.getIfNotHandled()?.run { showAddPhoto() }
        })

        viewModel.profileNavigation.observe(this, Observer {
            it.getIfNotHandled()?.run { showProfile() }
        })

        mainSharedViewModel.homeRedirection.observe(this, Observer {
            it.getIfNotHandled()?.run { bottomNavigation.selectedItemId = R.id.itemHome }
        })
    }


    private fun showProfile() {
        if (activeFragment is ProfileFragment) return

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment = supportFragmentManager.findFragmentByTag(ProfileFragment.TAG) as ProfileFragment?

        if (fragment == null) {
            fragment = ProfileFragment.newInstance()
            fragmentTransaction.add(R.id.containerFragment, fragment, ProfileFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) fragmentTransaction.hide(activeFragment as Fragment)

        fragmentTransaction.commit()

        activeFragment = fragment
    }


    private fun showAddPhoto() {
        if (activeFragment is PhotoFragment) return

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment = supportFragmentManager.findFragmentByTag(PhotoFragment.TAG) as PhotoFragment?

        if (fragment == null) {
            fragment = PhotoFragment.newInstance()
            fragmentTransaction.add(R.id.containerFragment, fragment, PhotoFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) fragmentTransaction.hide(activeFragment as Fragment)

        fragmentTransaction.commit()

        activeFragment = fragment
    }

    private fun showHome() {
        if (activeFragment is HomeFragment) return

        val fragmentTransaction = supportFragmentManager.beginTransaction()

        var fragment = supportFragmentManager.findFragmentByTag(HomeFragment.TAG) as HomeFragment?

        if (fragment == null) {
            fragment = HomeFragment.newInstance()
            fragmentTransaction.add(R.id.containerFragment, fragment, HomeFragment.TAG)
        } else {
            fragmentTransaction.show(fragment)
        }

        if (activeFragment != null) fragmentTransaction.hide(activeFragment as Fragment)

        fragmentTransaction.commit()

        activeFragment = fragment
    }

}
