package com.android.own.instag.ui.profile.adapter

import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import com.android.own.instag.data.model.Image
import com.android.own.instag.data.model.ProfilePost
import com.android.own.instag.data.remote.Networking
import com.android.own.instag.data.repository.UserRepository
import com.android.own.instag.ui.base.BaseItemViewModel
import com.android.own.instag.utils.display.ScreenUtils
import com.android.own.instag.utils.network.NetworkHelper
import com.android.own.instag.utils.rx.SchedulerProvider
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

class ProfilePostItemViewModel @Inject constructor(
    schedulerProvider: SchedulerProvider,
    compositeDisposable: CompositeDisposable,
    networkHelper: NetworkHelper,
    userRepository: UserRepository
) : BaseItemViewModel<ProfilePost>(schedulerProvider, compositeDisposable, networkHelper) {

    companion object {
        const val TAG = "ProfilePostItemViewModel"
    }

    private val user = userRepository.getCurrentUser()!!
    private val screenWidth = ScreenUtils.getScreenWidth()
    private val screenHeight = ScreenUtils.getScreenHeight()
    private val headers = mapOf(
        Pair(Networking.HEADER_API_KEY, Networking.API_KEY),
        Pair(Networking.HEADER_USER_ID, user.id),
        Pair(Networking.HEADER_ACCESS_TOKEN, user.accessToken)
    )


    val imageDetail: LiveData<Image> = Transformations.map(data) {
        Image(
            it.imageUrl,
            headers,
            screenWidth,
            it.imageHeight?.let { height ->
                return@let (calculateScaleFactor(it) * height).toInt()
            } ?: screenHeight / 3)
    }

    override fun onCreate() {

    }

    private fun calculateScaleFactor(post: ProfilePost) =
        post.imageWidth?.let { return@let screenWidth.toFloat() / it } ?: 1f
}
