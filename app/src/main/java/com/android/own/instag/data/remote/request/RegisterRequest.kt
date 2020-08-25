package com.android.own.instag.data.remote.request

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class RegisterRequest(
    @Expose
    @SerializedName("name")
    var name: String,

    @Expose
    @SerializedName("password")
    var password: String,

    @Expose
    @SerializedName("email")
    var email: String
)