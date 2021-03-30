package com.suhaili.submissiontwobfaa.model

import android.os.Parcelable
import kotlinx.android.parcel.Parcelize


@Parcelize
data class GitModel(
        var name: String = "",
        var avatar: String = "",
        var company: String = "",
        var follower: String = "",
        var following: String = "",
        var location: String = "",
        var repo: String = "",
        var username: String = ""
) : Parcelable