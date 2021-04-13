package com.suhaili.gitconsumer.modelview

import android.content.Context
import android.util.Log
import android.widget.Toast
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.suhaili.gitconsumer.R
import com.suhaili.gitconsumer.model.GitModel
import com.suhaili.gitconsumer.model.LoginModel
import com.suhaili.gitconsumer.serviceapi.APIRetro
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

class MainViewFragment : ViewModel() {

    private val livedata = MutableLiveData<ArrayList<GitModel>>()
    private val temp : ArrayList<GitModel> = arrayListOf()

    fun getLivedata():LiveData<ArrayList<GitModel>> = livedata

    private val URL = "https://api.github.com/"

    private fun getUserLogin(login: String, app: Context) {
        val retro = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retro.create(APIRetro::class.java)
        val data = api.getUserData(login)
        data.enqueue(object : Callback<GitModel> {
            override fun onResponse(call: Call<GitModel>, response: Response<GitModel>) {
                Log.d("Status", "API Connect Successfully")
                try {
                    var stat = false
                    val result = response.body()
                    if (result == null) {
                        Toast.makeText(app, R.string.peringatan.toString(), Toast.LENGTH_SHORT).show()
                    } else {
                        val dataGit = GitModel(
                            result.name.toString(),
                            result.avatar.toString(),
                            result.company.toString(),
                            result.follower.toString(),
                            result.following.toString(),
                            result.location.toString(),
                            result.repo.toString(),
                            result.username.toString())
                        for (i in 0 until temp.size) {
                            if (temp.get(i).username == dataGit.username) {
                                stat = true
                                break
                            }
                        }
                        if (stat == true) {

                        } else {
                            temp.add(dataGit)
                            livedata.postValue(temp)
                        }
                    }

                   // Log.d("status", "Successful Save Data")
                } catch (e: Exception) {
                   // Log.d("Status", "Fail to Save Data")
                   // Log.d("Status", e.toString())
                    Toast.makeText(app, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<GitModel>, t: Throwable) {
               // Log.d("Status", "API Connect Fail!")
               // Log.d("Status", t.message.toString())
                Toast.makeText(app, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }

    fun getFollow(username: String, app: Context) {
        temp.clear()
        val retro = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retro.create(APIRetro::class.java)
        val data = api.getFollower(username)
        data.enqueue(object : Callback<ArrayList<LoginModel>> {
            override fun onResponse(call: Call<ArrayList<LoginModel>>, response: Response<ArrayList<LoginModel>>) {
               // Log.d("Status", "API Connect Succesfully")
                try {
                    val result = response.body()
                    if (result != null) {
                        temp.clear()
                        for (i in 0 until result.size) {
                            val data = result.get(i).username
                            getUserLogin(data!!, app)
                        }
                    }
                    Toast.makeText(app, "Get Follower Data Succesfully Loaded!", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                  //  Log.d("Status", "${e.message}")
                    Toast.makeText(app, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<LoginModel>>, t: Throwable) {
               // Log.d("Status", "API Connect Fail")
               // Log.d("Status", "${t.message}")
            }
        })
    }

    fun getFollowing(username: String, app: Context) {
        val retro = Retrofit.Builder()
            .baseUrl(URL)
            .addConverterFactory(GsonConverterFactory.create())
            .build()

        val api = retro.create(APIRetro::class.java)
        val data = api.getFollowing(username)
        data.enqueue(object : Callback<ArrayList<LoginModel>> {
            override fun onResponse(call: Call<ArrayList<LoginModel>>, response: Response<ArrayList<LoginModel>>) {
              //  Log.d("Status", "API Connect Succesfully")
                try {
                    val result = response.body()
                    if (result != null) {
                        temp.clear()
                        for (i in 0 until result.size) {
                            val data = result.get(i).username
                            getUserLogin(data!!, app)
                        }
                    }
                 //  Log.d("Status", "Data Load Succesfully")
                    Toast.makeText(app, "Get Following Data Succesfully Loaded!", Toast.LENGTH_SHORT).show()
                } catch (e: Exception) {
                  //  Log.d("Status", "Data not Load Succesfully")
                  //  Log.d("Status", "${e.message}")
                    Toast.makeText(app, "${e.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<ArrayList<LoginModel>>, t: Throwable) {
               // Log.d("Status", "API Connect Fail")
               // Log.d("Status", "${t.message}")
                Toast.makeText(app, "${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }


}