package com.suhaili.gitconsumer.db

import android.net.Uri
import android.provider.BaseColumns

object DbDeclare {

    const val AUTHORITY = "com.suhaili.GitHubApp"
    const val SCHEME = "content"

    internal class FavCol : BaseColumns{
        companion object{
            const val TABLE_NAME = "favuser"
            const val _ID = "id"
            const val USERNAME = "username"
            const val NAME = "name"
            const val AVATAR_URL = "avatar"
            const val FOLLOWER = "follower"
            const val FOLLOWING = "following"
            const val LOCATION = "location"
            const val COMPANY = "company"
            const val REPOSITORY = "repo"

            val CONTENT_URI : Uri = Uri.Builder().scheme(SCHEME)
                    .authority(AUTHORITY)
                    .appendPath(TABLE_NAME)
                    .build()

        }
    }
}