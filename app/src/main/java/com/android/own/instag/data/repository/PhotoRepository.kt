package com.android.own.instag.data.repository

import com.android.own.instag.data.model.User
import com.android.own.instag.data.remote.NetworkService
import io.reactivex.Single
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.RequestBody
import java.io.File
import javax.inject.Inject

class PhotoRepository @Inject constructor(private val networkService: NetworkService) {

    fun uploadPhoto(file: File, user: User): Single<String> {
        return MultipartBody.Part.createFormData(
            "image", file.name, RequestBody.create(MediaType.parse("image/*"), file)
        ).run {
            return@run networkService.doImageUpload(this, user.id, user.accessToken)
                .map { it.data.imageUrl }
        }
    }
}