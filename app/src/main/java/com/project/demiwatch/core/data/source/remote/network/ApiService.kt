package com.project.demiwatch.core.data.source.remote.network

import com.project.demiwatch.core.data.source.remote.response.auth.LoginResponse
import com.project.demiwatch.core.data.source.remote.response.auth.RegisterResponse
import com.project.demiwatch.core.data.source.remote.response.RouteResponse
import com.project.demiwatch.core.data.source.remote.response.patient.PatientLocationResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.GET
import retrofit2.http.Header
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

    @GET("api/getLocation")
    suspend fun getLocationPatient(
        @Header("Authorization") token: String,
    ): PatientLocationResponse

    //END-PATIENT



}