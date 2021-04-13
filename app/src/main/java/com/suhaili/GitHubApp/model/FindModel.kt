package com.suhaili.GitHubApp.model

import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName

data class FindModel(
        @SerializedName("items")
        @Expose
        val items: ArrayList<LoginModel>
)