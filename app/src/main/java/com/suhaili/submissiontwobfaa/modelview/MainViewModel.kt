package com.suhaili.submissiontwobfaa.modelview

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suhaili.submissiontwobfaa.model.GitModel
import com.loopj.android.http.*
import cz.msebera.android.httpclient.Header
import org.json.JSONArray
import org.json.JSONObject


class MainViewModel : ViewModel() {

    private val livedata = MutableLiveData<ArrayList<GitModel>>()
    private val temp = ArrayList<GitModel>()

    fun getLiveData(): LiveData<ArrayList<GitModel>> = livedata

    fun getAllData() {
        val client = AsyncHttpClient()
        val URL = "https://api.github.com/users"
        val getData = ArrayList<GitModel>()
        client.addHeader("Authorization", "token d5ec42e2204a263f595473524ae942b906ccbfb8")
        client.addHeader("User-Agent", "GET_API_KEY")
        client.get(URL, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                Log.d("Status", "Success Connect API!")
                temp.clear()
                val result = String(responseBody!!)
                val jsonArr = JSONArray(result)
                try {
                    for (i in 0 until jsonArr.length()) {
                        val obj = jsonArr.getJSONObject(i)
                        getUserLogin(obj.getString("login").toString())
                    }

                    livedata.postValue(getData)
                    Log.d("Status", "Get Data Success!")

                } catch (e: Exception) {
                    Log.d("Status", "Get Data Failure!")
                    e.printStackTrace()
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("Status", "Failure Connect API!")
            }

        })

    }

    private fun getUserLogin(login: String) {
        val client = AsyncHttpClient()
        val URL = "https://api.github.com/users/$login"
        client.addHeader("Authorization", "token d5ec42e2204a263f595473524ae942b906ccbfb8")
        client.addHeader("User-Agent", "GET_API_KEY")
        client.get(URL, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                Log.d("Status", "Success Connect!")
                var stat = false
                var gitdata = GitModel()
                val hasil = String(responseBody!!)
                val objRes = JSONObject(hasil)
                gitdata.avatar = objRes.getString("avatar_url")
                gitdata.username = objRes.getString("login")
                gitdata.name = objRes.getString("name")
                gitdata.repo = objRes.getString("public_repos")
                gitdata.company = objRes.getString("company")
                gitdata.location = objRes.getString("location")
                gitdata.follower = objRes.getString("followers")
                gitdata.following = objRes.getString("following")
                Log.d("Status", "Get Data Success!")

                for (i in 0 until temp.size) {
                    if (temp.get(i).username == gitdata.username) {
                        stat = true
                        break
                    }
                }

                if (stat) {

                } else {
                    temp.add(gitdata)
                    livedata.postValue(temp)
                }

            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("Status", error?.message.toString())
                Log.d("Status", "Get Data Failure!")
            }

        })

    }

    fun findPeople(finding: String) {
        val client = AsyncHttpClient()
        val URL = "https://api.github.com/search/users?q=$finding"
        client.addHeader("Authorization", "token d5ec42e2204a263f595473524ae942b906ccbfb8")
        client.addHeader("User-Agent", "GET_API_KEY")
        client.get(URL, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                try {
                    Log.d("Status", "Success Connect API")
                    val hasil = String(responseBody!!)
                    val jsobj = JSONObject(hasil)
                    val item = jsobj.getJSONArray("items")
                    for (i in 0 until item.length()) {

                        val obj = item.getJSONObject(i)
                        getUserLogin(obj.getString("login"))
                        temp.clear()


                    }
                    Log.d("Status", "Get Data Success!")
                } catch (e: Exception) {
                    Log.d("Status", "Get Data Failure !")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("Status", "Failure Connect API!")
            }
        })

    }

    fun getFollow(username: String) {
        val client = AsyncHttpClient()
        val URL = "https://api.github.com/users/$username/followers"
        client.addHeader("Authorization", "token d5ec42e2204a263f595473524ae942b906ccbfb8")
        client.addHeader("User-Agent", "GET_API_KEY")
        client.get(URL, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                Log.d("Status", "Success Connect API!")
                try {
                    temp.clear()
                    val result = String(responseBody!!)
                    val jsonarr = JSONArray(result)
                    for (i in 0 until jsonarr.length()) {
                        val obj = jsonarr.getJSONObject(i)
                        getUserLogin(obj.getString("login").toString())

                    }
                    Log.d("Status", "Get Data Success!")

                } catch (e: Exception) {
                    Log.d("Status", "Get Data Failure!")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("Status", "Failure Connect API!")
            }
        })
    }

    fun getFollowing(username: String) {
        val client = AsyncHttpClient()
        val URL = "https://api.github.com/users/$username/following"
        client.addHeader("Authorization", "token d5ec42e2204a263f595473524ae942b906ccbfb8")
        client.addHeader("User-Agent", "GET_API_KEY")
        client.get(URL, object : AsyncHttpResponseHandler() {
            override fun onSuccess(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?
            ) {
                Log.d("Status", "Success Connect API!")
                try {
                    temp.clear()
                    val result = String(responseBody!!)
                    val jsonarr = JSONArray(result)
                    for (i in 0 until jsonarr.length()) {
                        val obj = jsonarr.getJSONObject(i)
                        getUserLogin(obj.getString("login").toString())
                    }
                    Log.d("Status", "Get Data Success!")
                } catch (e: Exception) {
                    Log.d("Status", "Get Data Failure!")
                }
            }

            override fun onFailure(
                statusCode: Int,
                headers: Array<out Header>?,
                responseBody: ByteArray?,
                error: Throwable?
            ) {
                Log.d("Status", "Failure Connect API!")
            }
        })
    }

}