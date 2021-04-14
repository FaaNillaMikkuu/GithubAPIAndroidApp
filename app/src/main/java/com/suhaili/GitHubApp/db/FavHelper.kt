package com.suhaili.GitHubApp.db

import android.content.ContentValues
import android.content.Context
import android.database.Cursor
import android.database.sqlite.SQLiteDatabase
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.TABLE_NAME
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion._ID

class FavHelper(ctx: Context) {

    companion object {
        private var INSTANCE: FavHelper? = null
        fun getInstance(ctx: Context): FavHelper =
                INSTANCE ?: synchronized(this) {
                    INSTANCE ?: FavHelper(ctx)
                }

        private lateinit var DbContract: DbHelper
        private lateinit var AccesDB: SQLiteDatabase
    }

    init {
        DbContract = DbHelper(ctx)
    }

    fun openDB() {
        AccesDB = DbContract.writableDatabase
    }

    fun closeDb() {
        if (AccesDB.isOpen) {
            DbContract.close()
            AccesDB.close()
        }
    }


    fun allQuery(): Cursor {
        return AccesDB.query(
                TABLE_NAME, null, null, null, null, null, "$_ID ASC"
        )
    }


    fun insertQuery(values: ContentValues?): Long {
        return AccesDB.insert(TABLE_NAME, null, values)
    }

    fun deleteQuery(id: String): Int {
        return AccesDB.delete(TABLE_NAME, "$_ID = '$id'", null)
    }



}