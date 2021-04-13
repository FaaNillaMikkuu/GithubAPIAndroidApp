package com.suhaili.GitHubApp.db

import android.content.Context
import android.database.sqlite.SQLiteDatabase
import android.database.sqlite.SQLiteOpenHelper
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.AVATAR_URL
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.COMPANY
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.FOLLOWER
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.FOLLOWING
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.LOCATION
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.NAME
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.REPOSITORY
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.TABLE_NAME
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.USERNAME
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion._ID

class DbHelper(ctx : Context):SQLiteOpenHelper(ctx, DB_NAME,null, DB_VERSION) {
    companion object{
        const val TB_NAME = TABLE_NAME
        const val DB_NAME = "db_fav_gituser"
        const val DB_VERSION = 1
        const val DB_CREATE = "CREATE TABLE $TB_NAME"+
                "($_ID INTEGER PRIMARY KEY AUTOINCREMENT,"+
                "$USERNAME TEXT NOT NULL," +
                "$NAME TEXT NOT NULL,"+
                "$AVATAR_URL TEXT NOT NULL,"+
                "$FOLLOWER TEXT NOT NULL," +
                "$FOLLOWING TEXT NOT NULL," +
                "$LOCATION TEXT NOT NULL," +
                "$COMPANY TEXT NOT NULL,"+
                "$REPOSITORY TEXT NOT NULL);"
        const val DB_DROP = "DROP TABLE IF EXISTS $TB_NAME"

    }

    override fun onCreate(db: SQLiteDatabase?) {
        db!!.execSQL(DB_CREATE)
    }

    override fun onUpgrade(db: SQLiteDatabase?, oldVersion: Int, newVersion: Int) {
        db!!.execSQL(DB_DROP)
        onCreate(db)
    }


}