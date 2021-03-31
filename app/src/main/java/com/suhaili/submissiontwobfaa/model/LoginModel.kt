package com.suhaili.submissiontwobfaa.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class LoginModel(
        @SerializedName("login")
        @Expose
        var username: String? = null
)
