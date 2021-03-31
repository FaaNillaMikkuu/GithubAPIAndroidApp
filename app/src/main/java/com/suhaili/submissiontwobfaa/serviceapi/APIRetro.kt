package com.suhaili.submissiontwobfaa.serviceapi

import com.suhaili.submissiontwobfaa.model.FindModel
import com.suhaili.submissiontwobfaa.model.GitModel
import com.suhaili.submissiontwobfaa.model.LoginModel
import retrofit2.Call
import retrofit2.http.*

interface APIRetro {
    @GET("users")
    @Headers("Authorization: token dd43785ed7eb42bc75973f3a8a837e3391122da9")
    fun getAllData(): Call<ArrayList<LoginModel>>


    @GET("users/{login}")
    @Headers("Authorization: token dd43785ed7eb42bc75973f3a8a837e3391122da9")
    fun getUserData(@Path("login") login: String): Call<GitModel>


    @GET("users/{login}/following")
    @Headers("Authorization: token dd43785ed7eb42bc75973f3a8a837e3391122da9")
    fun getFollowing(@Path("login") login: String): Call<ArrayList<LoginModel>>


    @GET("users/{login}/followers")
    @Headers("Authorization: token dd43785ed7eb42bc75973f3a8a837e3391122da9")
    fun getFollower(@Path("login") login: String): Call<ArrayList<LoginModel>>


    @GET("search/users")
    @Headers("Authorization: token dd43785ed7eb42bc75973f3a8a837e3391122da9")
    fun getFindPeople(@Query("q") q: String): Call<FindModel>

}