package com.project.demiwatch.core.data.source.remote.network

import com.project.demiwatch.core.data.source.remote.response.LoginResponse
import com.project.demiwatch.core.data.source.remote.response.RegisterResponse
import com.project.demiwatch.core.data.source.remote.response.RouteResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Query

interface ApiService{

    //USER

    @FormUrlEncoded
    @POST("api/register")
    suspend fun registerUser(
        @Field("name") name: String,
        @Field("email") email: String,
        @Field("password") password: String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("api/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginResponse

    //END-USER


    //PATIENT

    @GET("route/getRoute")
    fun getRoute(
        @Query("coordinates") coordinates:String
    ): RouteResponse

    //END-PATIENT



}