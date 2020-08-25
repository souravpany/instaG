package com.android.own.instag.di.module

import androidx.lifecycle.ViewModelProviders
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.android.own.instag.data.repository.*
import com.android.own.instag.di.TempDirectory
import com.android.own.instag.ui.base.BaseFragment
import com.android.own.instag.ui.home.HomeViewModel
import com.android.own.instag.ui.home.posts.PostsAdapter
import com.android.own.instag.ui.main.MainSharedViewModel
import com.android.own.instag.ui.photo.PhotoViewModel
import com.android.own.instag.ui.profile.ProfileViewModel
import com.android.own.instag.ui.profile.adapter.ProfilePostsAdapter
import com.android.own.instag.utils.ViewModelProviderFactory
import com.android.own.instag.utils.network.NetworkHelper
import com.android.own.instag.utils.rx.SchedulerProvider
import com.mindorks.paracamera.Camera
import dagger.Module
import dagger.Provides
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.processors.PublishProcessor
import java.io.File


@Module
class FragmentModule(private val fragment: BaseFragment<*>) {

    @Provides
    fun provideLinearLayoutManager(): LinearLayoutManager = LinearLayoutManager(fragment.context)

    @Provides
    fun provideGridLayoutManager(): GridLayoutManager =
        GridLayoutManager(fragment.context, 3)


    @Provides
    fun providePostsAdapter() = PostsAdapter(fragment.lifecycle, ArrayList())

    @Provides
    fun provideProfilePostsAdapter() = ProfilePostsAdapter(fragment.lifecycle, ArrayList())

    @Provides
    fun provideCamera() = Camera.Builder()
        .resetToCorrectOrientation(true)// it will rotate the camera bitmap to the correct orientation from meta data
        .setTakePhotoRequestCode(1)
        .setDirectory("temp")
        .setName("camera_temp_img")
        .setImageFormat(Camera.IMAGE_JPEG)
        .setCompression(75)
        .setImageHeight(500)// it will try to achieve this height as close as possible maintaining the aspect ratio;
        .build(fragment)

    @Provides
    fun provideHomeViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper,
        userRepository: UserRepository,
        postRepository: PostRepository
    ): HomeViewModel = ViewModelProviders.of(
        fragment, ViewModelProviderFactory(HomeViewModel::class) {
            HomeViewModel(
                schedulerProvider, compositeDisposable, networkHelper, userRepository,
                postRepository, ArrayList(), PublishProcessor.create()
            )
        }).get(HomeViewModel::class.java)


    @Provides
    fun provideProfileViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        userPostRepository: UserPostRepository,
        networkHelper: NetworkHelper,
        userRepository: UserRepository,
        profileUploadRepository: ProfileUploadRepository,
        @TempDirectory directory: File
    ): ProfileViewModel = ViewModelProviders.of(
        fragment, ViewModelProviderFactory(ProfileViewModel::class) {
            ProfileViewModel(
                schedulerProvider,
                compositeDisposable,
                networkHelper,
                userRepository,
                userPostRepository,
                ArrayList(), profileUploadRepository, directory
            )
        }).get(ProfileViewModel::class.java)


    @Provides
    fun providePhotoViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        userRepository: UserRepository,
        photoRepository: PhotoRepository,
        postRepository: PostRepository,
        networkHelper: NetworkHelper,
        @TempDirectory directory: File
    ): PhotoViewModel = ViewModelProviders.of(
        fragment, ViewModelProviderFactory(PhotoViewModel::class) {
            PhotoViewModel(
                schedulerProvider, compositeDisposable, userRepository,
                photoRepository, postRepository, networkHelper, directory
            )
        }).get(PhotoViewModel::class.java)


    @Provides
    fun provideMainSharedViewModel(
        schedulerProvider: SchedulerProvider,
        compositeDisposable: CompositeDisposable,
        networkHelper: NetworkHelper
    ): MainSharedViewModel = ViewModelProviders.of(
        fragment.activity!!, ViewModelProviderFactory(MainSharedViewModel::class) {
            MainSharedViewModel(schedulerProvider, compositeDisposable, networkHelper)
        }).get(MainSharedViewModel::class.java)
}