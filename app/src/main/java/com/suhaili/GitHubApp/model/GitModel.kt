package com.suhaili.GitHubApp.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class GitModel(
        @SerializedName("name")
        @Expose
        var name: String? = null,
        @SerializedName("avatar_url")
        @Expose
        var avatar: String? = null,
        @SerializedName("company")
        @Expose
        var company: String? = null,
        @SerializedName("followers")
        @Expose
        var follower: String? = null,
        @SerializedName("following")
        @Expose
        var following: String? = null,
        @SerializedName("location")
        @Expose
        var location: String? = null,
        @SerializedName("public_repos")
        @Expose
        var repo: String? = null,
        @SerializedName("login")
        @Expose
        var username: String? = null
) : Parcelable