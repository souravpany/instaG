package com.android.own.instag.data.repository

import com.android.own.instag.data.model.ProfilePost
import com.android.own.instag.data.model.User
import com.android.own.instag.data.remote.NetworkService
import io.reactivex.Single
import javax.inject.Inject

class UserPostRepository @Inject constructor(
    private val networkService: NetworkService
) {

    fun fetchProfilePostList(
        user: User
    ): Single<List<ProfilePost>> {
        return networkService.doProfilePostListCall(
            user.id,
            user.accessToken
        ).map { it.data }
    }
}