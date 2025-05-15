package com.example.my_track_fit

import android.os.Bundle // Necesario para el método onCreate
import android.widget.Button // Para vincular el botón de registro
import android.widget.EditText // Para vincular los campos de texto de usuario, contraseña y confirmación
import android.widget.Toast // Para mostrar mensajes al usuario
import androidx.appcompat.app.AppCompatActivity // Porque SignUpActivity extiende AppCompatActivity
import com.example.my_track_fit.network.ApiService // Para usar Retrofit
import com.example.my_track_fit.network.LoginRequest // Para enviar datos al backend
import com.example.my_track_fit.network.RetrofitClient // Para crear la instancia de Retrofit
import retrofit2.Call // Para manejar las llamadas de Retrofit
import retrofit2.Callback // Para manejar las respuestas de Retrofit
import retrofit2.Response // Para manejar las respuestas de Retrofit


class SignUpActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_signup)

        val usernameEditText = findViewById<EditText>(R.id.etSignUpUsername)
        val passwordEditText = findViewById<EditText>(R.id.etSignUpPassword)
        val confirmPasswordEditText = findViewById<EditText>(R.id.etConfirmPassword)
        val signUpButton = findViewById<Button>(R.id.btnSignUp)

        signUpButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()
            val confirmPassword = confirmPasswordEditText.text.toString()

            if (username.isNotEmpty() && password.isNotEmpty() && password == confirmPassword) {
                signUpUser(username, password)
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos correctamente", Toast.LENGTH_SHORT).show()
            }
        }
    }

    private fun signUpUser(username: String, password: String) {
        val apiService: ApiService = RetrofitClient.instance.create(ApiService::class.java)
        val signUpRequest = LoginRequest(username, password)

        apiService.signUpUser(signUpRequest).enqueue(object : retrofit2.Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: retrofit2.Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@SignUpActivity, "Registro exitoso", Toast.LENGTH_SHORT).show()
                    finish() // Regresar a la pantalla de login
                } else {
                    Toast.makeText(this@SignUpActivity, "Error: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@SignUpActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}