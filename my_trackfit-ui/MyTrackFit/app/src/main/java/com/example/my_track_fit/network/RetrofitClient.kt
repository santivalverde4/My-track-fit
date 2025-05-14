package com.example.my_track_fit.network

import retrofit2.Call
import retrofit2.http.Body
import retrofit2.http.POST

// Define los datos que enviarás al backend
data class LoginRequest(val Username: String, val Password: String)

// Define la respuesta que recibirás del backend
data class LoginResponse(val success: Boolean, val message: String, val Id: Int?)

// Define las rutas de tu API
interface ApiService {
    @POST("login") // Ruta del endpoint en tu backend
    fun loginUser(@Body request: LoginRequest): Call<LoginResponse>
}