package com.android.own.instag.data.remote.response

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class UploadImageResponse(

    @Expose
    @SerializedName("status")
    var status: Int,

    @Expose
    @SerializedName("statusCode")
    var statusCode: String,

    @Expose
    @SerializedName("message")
    var message: String
) /*{

    data class ImageDetails(
        @Expose
        @SerializedName("imageUrl")
        val imageUrl: String
    )
}
*/