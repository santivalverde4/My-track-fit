package com.example.my_track_fit

class LoginActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_login)

        // Vincular los elementos de la interfaz
        val usernameEditText = findViewById<EditText>(R.id.etUsername)
        val passwordEditText = findViewById<EditText>(R.id.etPassword)
        val loginButton = findViewById<Button>(R.id.btnLogin)

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
    }

    private fun loginUser(username: String, password: String) {
    val apiService = RetrofitClient.instance.create(ApiService::class.java)
    val loginRequest = LoginRequest(username, password)

    apiService.loginUser(loginRequest).enqueue(object : retrofit2.Callback<LoginResponse> {
        override fun onResponse(call: Call<LoginResponse>, response: retrofit2.Response<LoginResponse>) {
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