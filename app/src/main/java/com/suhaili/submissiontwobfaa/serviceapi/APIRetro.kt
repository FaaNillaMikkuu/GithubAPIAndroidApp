package com.suhaili.submissiontwobfaa.serviceapi

import com.suhaili.submissiontwobfaa.model.FindModel
import com.suhaili.submissiontwobfaa.model.GitModel
import com.suhaili.submissiontwobfaa.model.LoginModel
import retrofit2.Call
import retrofit2.http.*

interface APIRetro {
    @GET("users")
    @Headers("Authorization: token d8dc3faf2edb4a4a85f5d70ff8cceafba9a0618f")
    fun getAllData(): Call<ArrayList<LoginModel>>


    @GET("users/{login}")
    @Headers("Authorization: token d8dc3faf2edb4a4a85f5d70ff8cceafba9a0618f")
    fun getUserData(@Path("login") login: String): Call<GitModel>


    @GET("users/{login}/following")
    @Headers("Authorization: token d8dc3faf2edb4a4a85f5d70ff8cceafba9a0618f")
    fun getFollowing(@Path("login") login: String): Call<ArrayList<LoginModel>>


    @GET("users/{login}/followers")
    @Headers("Authorization: token d8dc3faf2edb4a4a85f5d70ff8cceafba9a0618f")
    fun getFollower(@Path("login") login: String): Call<ArrayList<LoginModel>>


    @GET("search/users")
    @Headers("Authorization: token d8dc3faf2edb4a4a85f5d70ff8cceafba9a0618f")
    fun getFindPeople(@Query("q") q: String): Call<FindModel>

}