package com.android.own.instag.data.remote.response

import com.android.own.instag.data.model.ProfilePost
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class ProfilePostListResponse(
    @Expose
    @SerializedName("statusCode")
    var statusCode: String,

    @Expose
    @SerializedName("message")
    var message: String,

    @Expose
    @SerializedName("data")
    val data: List<ProfilePost>
)