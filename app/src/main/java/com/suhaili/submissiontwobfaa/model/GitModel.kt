package com.suhaili.submissiontwobfaa.model

import android.os.Parcelable
import com.google.gson.annotations.Expose
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize


@Parcelize
data class GitModel(
        @SerializedName("name")
        @Expose
        val name: String? = null,
        @SerializedName("avatar_url")
        @Expose
        val avatar: String? = null,
        @SerializedName("company")
        @Expose
        val company: String? = null,
        @SerializedName("followers")
        @Expose
        val follower: String? = null,
        @SerializedName("following")
        @Expose
        val following: String? = null,
        @SerializedName("location")
        @Expose
        val location: String? = null,
        @SerializedName("public_repos")
        @Expose
        val repo: String? = null,
        @SerializedName("login")
        @Expose
        val username: String? = null
) : Parcelable