package com.example.my_track_fit

import android.content.Intent // Necesario para navegar a otra actividad
import android.os.Bundle // Necesario para el método onCreate
import android.widget.Button // Necesario para vincular el botón de inicio de sesión
import android.widget.EditText // Necesario para vincular los campos de texto
import android.widget.TextView // Necesario para vincular el TextView de registro
import android.widget.Toast // Necesario para mostrar mensajes al usuario
import androidx.appcompat.app.AppCompatActivity // Necesario porque LoginActivity extiende AppCompatActivity
import com.example.my_track_fit.network.ApiService // Necesario para usar Retrofit
import com.example.my_track_fit.network.LoginRequest // Necesario para enviar datos al backend
import com.example.my_track_fit.network.RetrofitClient // Necesario para crear la instancia de Retrofit
import retrofit2.Call // Necesario para manejar las llamadas de Retrofit
import retrofit2.Callback // Necesario para manejar las respuestas de Retrofit
import retrofit2.Response // Necesario para manejar las respuestas de Retrofit

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Vincular los elementos de la interfaz
        val usernameEditText = findViewById<EditText>(R.id.etUsername)
        val passwordEditText = findViewById<EditText>(R.id.etPassword)
        val loginButton = findViewById<Button>(R.id.btnLogin)
        val signUpTextView = findViewById<TextView>(R.id.tvGoToSignUp) // Vincular el TextView para registro

        // Configurar el botón de inicio de sesión
        loginButton.setOnClickListener {
            val username = usernameEditText.text.toString()
            val password = passwordEditText.text.toString()

            // Validar los datos ingresados
            if (username.isNotEmpty() && password.isNotEmpty()) {
                loginUser(username, password) // Llamar al método para enviar los datos al backend
            } else {
                Toast.makeText(this, "Por favor, completa todos los campos", Toast.LENGTH_SHORT).show()
            }
        }

        // Configurar el enlace para ir a la pantalla de registro
        signUpTextView.setOnClickListener {
            val intent = Intent(this, SignUpActivity::class.java)
            startActivity(intent)
        }
    }

    private fun loginUser(username: String, password: String) {
        val apiService = RetrofitClient.instance.create(ApiService::class.java)
        val loginRequest = LoginRequest(username, password)

        apiService.loginUser(loginRequest).enqueue(object : Callback<LoginResponse> {
            override fun onResponse(call: Call<LoginResponse>, response: Response<LoginResponse>) {
                if (response.isSuccessful && response.body()?.success == true) {
                    Toast.makeText(this@LoginActivity, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show()
                    // Redirigir al usuario a la siguiente pantalla
                } else {
                    Toast.makeText(this@LoginActivity, "Error: ${response.body()?.message}", Toast.LENGTH_SHORT).show()
                }
            }

            override fun onFailure(call: Call<LoginResponse>, t: Throwable) {
                Toast.makeText(this@LoginActivity, "Error de conexión: ${t.message}", Toast.LENGTH_SHORT).show()
            }
        })
    }
}