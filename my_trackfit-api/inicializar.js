import { initConnection } from "./conexion_bd.js"; // Importa la conexión a la base de datos
import express from "express"; // Si usas Express para manejar rutas
import dotenv from "dotenv";

dotenv.config();

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware para parsear JSON
app.use(express.json());

// Ruta de prueba
app.get("/", (req, res) => {
  res.send("API is running...");
});

// Inicializar la conexión a la base de datos y el servidor
(async () => {
  try {
    await initConnection(); // Inicializa la conexión a la base de datos
    app.listen(PORT, () => {
      console.log(`Server is running on http://localhost:${PORT}`);
    });
  } catch (error) {
    console.error("Failed to start the server:", error);
  }
})();