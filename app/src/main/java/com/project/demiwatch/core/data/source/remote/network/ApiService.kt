package com.project.demiwatch.core.data.source.remote.network

import com.project.demiwatch.core.data.source.remote.response.auth.LoginResponse
import com.project.demiwatch.core.data.source.remote.response.auth.RegisterResponse
import com.project.demiwatch.core.data.source.remote.response.patient.PatientHistoryResponse
import com.project.demiwatch.core.data.source.remote.response.patient.PatientLocationResponse
import com.project.demiwatch.core.data.source.remote.response.patient.PatientResponse
import com.project.demiwatch.core.data.source.remote.response.user.UserResponse
import retrofit2.http.*

interface ApiService {
    //USER

    @FormUrlEncoded
    @POST("api/register")
    suspend fun registerUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): RegisterResponse

    @FormUrlEncoded
    @POST("api/login")
    suspend fun loginUser(
        @Field("email") email: String,
        @Field("password") password: String,
    ): LoginResponse

    @FormUrlEncoded
    @PUT("api/addUser/{id}")
    suspend fun addUser(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Field("nama") name: String,
        @Field("telepon") phoneNumber: String,
        @Field("status") userStatus: String,
        @Field("radius") maxRadius: String,
    ): UserResponse

    @FormUrlEncoded
    @PUT("api/addUser/{id}")
    suspend fun updateUser(
        @Header("Authorization") token: String,
        @Path("id") id: String,
        @Field("nama") name: String,
        @Field("telepon") phoneNumber: String,
        @Field("status") userStatus: String,
        @Field("radius") maxRadius: String,
    ): UserResponse

    @GET("api/getUser/{id}")
    suspend fun getUser(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): UserResponse

    //END-USER


    //PATIENT
    @GET("api/getLocation/{id}")
    suspend fun getLocationPatient(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): PatientLocationResponse

    @FormUrlEncoded
    @POST("api/addPatient")
    suspend fun addPatient(
        @Header("Authorization") token: String,
        @Field("nama") name: String,
        @Field("umur") age: Int,
        @Field("jenisPenyakit") symptom: String,
        @Field("catatan") note: String,
        @Field("kode") watchCode: String,

        //use JSON string
        @Field("alamatRumah") homeAddress: String,
        @Field("alamatTujuan") destinationAddress: String,
    ): PatientResponse

    @FormUrlEncoded
    @PUT("api/addPatient/{id}")
    suspend fun updatePatient(
        @Path("id") id: String,
        @Header("Authorization") token: String,
        @Field("nama") name: String,
        @Field("umur") age: Int,
        @Field("jenisPenyakit") symptom: String,
        @Field("catatan") note: String,
        @Field("kode") watchCode: String,

        //use JSON string
        @Field("alamatRumah") homeAddress: String,
        @Field("alamatTujuan") destinationAddress: String,
    ): PatientResponse

    @FormUrlEncoded
    @PUT("api/addPatient/{id}")
    suspend fun updatePatientLocations(
        @Path("id") id: String,
        @Header("Authorization") token: String,

        //use JSON string
        @Field("alamatRumah") homeAddress: String,
        @Field("alamatTujuan") destinationAddress: String,
    ): PatientResponse

    @GET("api/getPatient/{id}")
    suspend fun getPatient(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): PatientResponse

    @GET("api/history/{id}")
    suspend fun getHistoryPatient(
        @Header("Authorization") token: String,
        @Path("id") id: String,
    ): PatientHistoryResponse

    //END-PATIENT


}