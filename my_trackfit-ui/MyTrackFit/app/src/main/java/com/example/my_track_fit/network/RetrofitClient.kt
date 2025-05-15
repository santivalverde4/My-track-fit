package com.example.my_track_fit.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Define los datos que se envían al backend
data class LoginRequest(val Username: String, val Password: String)

// Define la respuesta que se recibe del backend
data class LoginResponse(val success: Boolean, val message: String, val Id: Int?)

// Define las rutas del API
interface ApiService {
    @POST("login") // Ruta del endpoint en el backend para login
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>

    @POST("signup") // Ruta del endpoint en el backend para sign-up
    fun signUpUser(@Body request: LoginRequest): Call<LoginResponse>
}