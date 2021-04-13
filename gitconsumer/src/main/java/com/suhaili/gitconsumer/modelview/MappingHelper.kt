package com.suhaili.gitconsumer.modelview

import android.database.Cursor
import com.suhaili.gitconsumer.db.DbDeclare.FavCol.Companion.AVATAR_URL
import com.suhaili.gitconsumer.db.DbDeclare.FavCol.Companion.COMPANY
import com.suhaili.gitconsumer.db.DbDeclare.FavCol.Companion.FOLLOWER
import com.suhaili.gitconsumer.db.DbDeclare.FavCol.Companion.FOLLOWING
import com.suhaili.gitconsumer.db.DbDeclare.FavCol.Companion.LOCATION
import com.suhaili.gitconsumer.db.DbDeclare.FavCol.Companion.NAME
import com.suhaili.gitconsumer.db.DbDeclare.FavCol.Companion.REPOSITORY
import com.suhaili.gitconsumer.db.DbDeclare.FavCol.Companion.USERNAME
import com.suhaili.gitconsumer.db.DbDeclare.FavCol.Companion._ID
import com.suhaili.gitconsumer.model.FavoritModel

object MappingHelper {

    fun CursorToArrayList(value: Cursor): ArrayList<FavoritModel> {
        val arrList = ArrayList<FavoritModel>()
        if (value.count > 0) {
            value.moveToFirst()
            do {
                val model = FavoritModel()
                model._id = value.getInt(value.getColumnIndexOrThrow(_ID))
                model.username = value.getString(value.getColumnIndexOrThrow(USERNAME))
                model.name = value.getString(value.getColumnIndexOrThrow(NAME))
                model.avatar = value.getString(value.getColumnIndexOrThrow(AVATAR_URL))
                model.follower = value.getString(value.getColumnIndexOrThrow(FOLLOWER))
                model.following = value.getString(value.getColumnIndexOrThrow(FOLLOWING))
                model.location = value.getString(value.getColumnIndexOrThrow(LOCATION))
                model.repo = value.getString(value.getColumnIndexOrThrow(REPOSITORY))
                model.company = value.getString(value.getColumnIndexOrThrow(COMPANY))
                arrList.add(model)
                value.moveToNext()
            } while (!value.isAfterLast)
        }
        return arrList
    }


}