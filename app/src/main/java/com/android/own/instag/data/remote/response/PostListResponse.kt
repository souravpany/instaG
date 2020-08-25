package com.android.own.instag.data.remote.response

import com.android.own.instag.data.model.Post
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class PostListResponse(
    @Expose
    @SerializedName("statusCode")
    var statusCode: String,

    @Expose
    @SerializedName("message")
    var message: String,

    @Expose
    @SerializedName("data")
    val data: List<Post>
)