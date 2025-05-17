const express = require("express");
const router = express.Router();
const AuthService = require("../autenticacion"); // Archivo donde haces los llamados a la base de datos

// Endpoint para login
router.post("/login", async (req, res) => {
    const { Username, Password } = req.body;

    try {
        const authService = new AuthService();
        const result = await authService.loginUser({ Username, Password });

        if (result.success) {
            res.status(200).json(result); // Respuesta exitosa
        } else {
            res.status(401).json({ success: false, message: "Credenciales inválidas" });
        }
    } catch (error) {
        res.status(500).json({ success: false, message: "Error interno del servidor" });
    }
});

// Endpoint para sign-up
router.post("/signup", async (req, res) => {
    const { Username, Password } = req.body;

    try {
        const authService = new AuthService();
        const result = await authService.signUpUser({ Username, Password });

        if (result.success) {
            res.status(201).json(result); // Usuario creado exitosamente
        } else {
            res.status(400).json({ success: false, message: result.message }); // Error en el registro
        }
    } catch (error) {
        res.status(500).json({ success: false, message: "Error interno del servidor" });
    }
});

module.exports = router;