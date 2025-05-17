const express = require("express");
const dotenv = require("dotenv");
const { DataSource } = require("typeorm");
const AuthService = require("./autenticacion"); // Importa la clase de autenticación
const createAuthRouter = require("./routes/auth"); // Importa la función que crea el router

dotenv.config();

const app = express();
const PORT = process.env.PORT || 3000;

// Middleware para parsear JSON
app.use(express.json());

// Configuración de la conexión a la base de datos
const AppDataSource = new DataSource({
  type: "mssql",
  host: process.env.SQL_SERVER,
  port: Number(process.env.SQL_PORT) || 1433,
  username: process.env.SQL_USER,
  password: process.env.SQL_PASSWORD,
  database: process.env.SQL_DATABASE,
  synchronize: false,
  logging: true,
  options: {
    encrypt: true,
    trustServerCertificate: true,
  },
});

// Inicializar la conexión a la base de datos
async function initConnection() {
  try {
    console.log("Connecting to the database...");
    await AppDataSource.initialize();
    console.log("Database connection successful.");
  } catch (error) {
    console.error("Connection failed due to: " + error);
    throw error;
  }
}

// Ejecutar un procedimiento almacenado
async function execute(storedProcedure, inParams = {}, outParams = {}) {
  try {
    const queryRunner = AppDataSource.createQueryRunner();
    await queryRunner.connect();

    const inputParams = Object.keys(inParams)
      .map((key) => `@${key} = '${inParams[key]}'`)
      .join(", ");
    const outputParams = Object.keys(outParams)
      .map((key) => `@${key} OUTPUT`)
      .join(", ");
    const query = `EXEC ${storedProcedure} ${inputParams} ${
      outputParams ? ", " + outputParams : ""
    }`;

    const result = await queryRunner.query(query);

    await queryRunner.release();
    return result;
  } catch (error) {
    console.error("Query failed due to: " + error);
    throw error;
  }
}

// Instancia de AuthService con la función execute
const authService = new AuthService(execute);

// Usa el router pasando la instancia de AuthService
const authRouter = createAuthRouter(authService);
app.use("/api", authRouter);

// Ruta de prueba
app.get("/", (req, res) => {
  res.send("API is running...");
});

// Inicializar la conexión y levantar el servidor
(async () => {
  try {
    await initConnection();
    app.listen(PORT, "0.0.0.0", () => {
      console.log(`Server is running on http://0.0.0.0:${PORT}`);
    });
  } catch (error) {
    console.error("Failed to start the server:", error);
  }
})();