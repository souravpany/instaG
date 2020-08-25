package com.android.own.instag.data.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import java.util.*

data class ProfilePost(
    @Expose
    @SerializedName("id")
    val id: String,

    @Expose
    @SerializedName("imgUrl")
    val imageUrl: String,

    @Expose
    @SerializedName("imgWidth")
    val imageWidth: Int?,

    @Expose
    @SerializedName("imgHeight")
    val imageHeight: Int?,


    @Expose
    @SerializedName("createdAt")
    val createdAt: Date
)