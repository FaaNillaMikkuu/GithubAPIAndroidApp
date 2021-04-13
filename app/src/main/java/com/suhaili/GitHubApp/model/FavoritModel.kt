package com.suhaili.GitHubApp.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize


@Parcelize
class FavoritModel(
    var _id : Int = 0,
    var username: String? = null,
    var name: String? = null,
    var avatar: String? = null,
    var company: String? = null,
    var follower: String? = null,
    var following: String? = null,
    var location: String? = null,
    var repo: String? = null,

):Parcelable