import { initConnection } from "./conexion_bd.js";
import express from "express";
import dotenv from "dotenv";
import authRoutes from "../routes/auth.js";

dotenv.config();

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware para parsear JSON
app.use(express.json());

// Conectar las rutas de autenticación al prefijo /api
app.use("/api", authRoutes);

// Ruta de prueba
app.get("/", (req, res) => {
  res.send("API is running...");
});

// Inicializar la conexión a la base de datos y el servidor
(async () => {
  try {
    await initConnection(); // Inicializa la conexión a la base de datos
    app.listen(PORT, "0.0.0.0", () => {
      console.log(`Server is running on http://0.0.0.0:${PORT}`);
    });
  } catch (error) {
    console.error("Failed to start the server:", error);
  }
})();

