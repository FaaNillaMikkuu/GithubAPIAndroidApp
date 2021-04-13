package com.suhaili.GitHubApp.provider

import android.content.ContentProvider
import android.content.ContentValues
import android.content.Context
import android.content.UriMatcher
import android.database.Cursor
import android.net.Uri
import com.suhaili.GitHubApp.db.DbDeclare.AUTHORITY
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.CONTENT_URI
import com.suhaili.GitHubApp.db.DbDeclare.FavCol.Companion.TABLE_NAME
import com.suhaili.GitHubApp.db.FavHelper

class ProviderGit : ContentProvider() {

    companion object {
        private const val USER = 1
        private const val USER_ID = 2
        private val mUriMatcher = UriMatcher(UriMatcher.NO_MATCH)
        private lateinit var accessHelper: FavHelper
    }


    init {
        mUriMatcher.addURI(AUTHORITY, "$TABLE_NAME", USER)
        mUriMatcher.addURI(AUTHORITY, "$TABLE_NAME/#", USER_ID)
    }

    override fun delete(uri: Uri, selection: String?, selectionArgs: Array<String>?): Int {
        val deleted : Int = when(USER_ID){
            mUriMatcher.match(uri) -> {
                accessHelper.deleteQuery(uri.lastPathSegment.toString())
            }
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI,null)
        return deleted
    }

    override fun getType(uri: Uri): String? {
       return null
    }

    override fun insert(uri: Uri, values: ContentValues?): Uri? {
        val Add: Long = when (USER) {
            mUriMatcher.match(uri) -> accessHelper.insertQuery(values)
            else -> 0
        }
        context?.contentResolver?.notifyChange(CONTENT_URI,null)
        return  Uri.parse("$CONTENT_URI/$Add")
    }

    override fun onCreate(): Boolean {
        accessHelper = FavHelper.getInstance(context as Context)
        accessHelper.openDB()
        return true
    }

    override fun query(uri: Uri, projection: Array<String>?, selection: String?,
                       selectionArgs: Array<String>?, sortOrder: String?): Cursor? {
        return when (mUriMatcher.match(uri)) {
            USER -> {
               accessHelper.allQuery()
            }
            else -> null
        }
    }

    override fun update(uri: Uri, values: ContentValues?, selection: String?,
                        selectionArgs: Array<String>?): Int {
        return 0
    }
}