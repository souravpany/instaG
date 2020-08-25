package com.android.own.instag.ui.profile

import androidx.lifecycle.MutableLiveData
import com.android.own.instag.R
import com.android.own.instag.data.model.ProfilePost
import com.android.own.instag.data.repository.ProfileUploadRepository
import com.android.own.instag.data.repository.UserPostRepository
import com.android.own.instag.data.repository.UserRepository
import com.android.own.instag.ui.base.BaseViewModel
import com.android.own.instag.utils.common.FileUtils
import com.android.own.instag.utils.common.Resource
import com.android.own.instag.utils.network.NetworkHelper
import com.android.own.instag.utils.rx.SchedulerProvider
import io.reactivex.Single
import io.reactivex.disposables.CompositeDisposable
import java.io.File
import java.io.InputStream

class ProfileViewModel(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    private val userRepository: UserRepository,
    private val userPostRepository: UserPostRepository,
    private val allPostList: ArrayList<ProfilePost>,
    private val profileUploadRepository: ProfileUploadRepository,
    private val directory: File
) : BaseViewModel(schedulerProvider, compositeDisposable, networkHelper) {


    val childImageField: MutableLiveData<String> = MutableLiveData()


    val profilePosts: MutableLiveData<Resource<List<ProfilePost>>> = MutableLiveData()


    override fun onCreate() {

        if (checkInternetConnectionWithMessage()) {
            compositeDisposable.addAll(
                userPostRepository.fetchProfilePostList(userRepository.getCurrentUser()!!)
                    .subscribeOn(schedulerProvider.io())
                    .subscribe(
                        {
                            allPostList.addAll(it)
                            profilePosts.postValue(Resource.success(it))
                        },
                        {
                            handleNetworkError(it)
                        }

                    )

            )
        }

    }


    fun onGalleryImageSelected(inputStream: InputStream) {

        compositeDisposable.add(
            Single.fromCallable {
                FileUtils.saveInputStreamToFile(
                    inputStream, directory, "gallery_img_temp", 100
                )
            }
                .subscribeOn(schedulerProvider.io())
                .subscribe(
                    {
                        if (it != null) {
                            FileUtils.getImageSize(it)?.run {
                                uploadVaccinePhoto(it)
                                childImageField.postValue(it.path)
                            }

                        } else {
                            messageStringId.postValue(Resource.error(R.string.try_again))

                        }
                    },
                    {
                        messageStringId.postValue(Resource.error(R.string.try_again))
                    }
                )
        )

    }

    fun onCameraImageTaken(cameraImageProcessor: () -> String) {

        compositeDisposable.add(
            Single.fromCallable { cameraImageProcessor() }
                .subscribeOn(schedulerProvider.io())
                .subscribe(
                    {
                        File(it).apply {
                            FileUtils.getImageSize(this)?.let {
                                uploadVaccinePhoto(this)
                                childImageField.postValue(this.toString())
                            }
                        }
                    },
                    {
                        messageStringId.postValue(Resource.error(R.string.try_again))
                    }
                )
        )

    }


    private fun uploadVaccinePhoto(imageFile: File) {

        compositeDisposable.add(
            profileUploadRepository.uploadVaccinePhoto(imageFile, userRepository.getCurrentUser()!!)
            !!.subscribeOn(schedulerProvider.io())
                .subscribe(
                    {
                        messageString.postValue(Resource.success(it.message))
                    },
                    {
                        handleNetworkError(it)
                    }
                )

        )


    }
}