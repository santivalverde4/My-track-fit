import { DataSource } from "typeorm";
import { config as dotenvConfig } from "dotenv";

dotenvConfig();

// Configuración de la conexión a la base de datos
const AppDataSource = new DataSource({
  type: "mssql",
  host: process.env.SQL_SERVER,
  port: Number(process.env.SQL_PORT) || 1433,
  username: process.env.SQL_USER,
  password: process.env.SQL_PASSWORD,
  database: process.env.SQL_DATABASE,
  synchronize: false, // Cambiar a true solo en desarrollo para sincronizar entidades automáticamente
  logging: true, // Cambiar a false en producción
  options: {
    encrypt: true,
    trustServerCertificate: true,
  },
});

// Inicializar la conexión a la base de datos
export async function initConnection() {
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
export async function execute(storedProcedure, inParams = {}, outParams = {}) {
  try {
    const queryRunner = AppDataSource.createQueryRunner();
    await queryRunner.connect();

    // Construir la consulta para el procedimiento almacenado
    const inputParams = Object.keys(inParams)
      .map((key) => `@${key} = '${inParams[key]}'`)
      .join(", ");
    const outputParams = Object.keys(outParams)
      .map((key) => `@${key} OUTPUT`)
      .join(", ");
    const query = `EXEC ${storedProcedure} ${inputParams} ${
      outputParams ? ", " + outputParams : ""
    }`;

    // Ejecutar la consulta
    const result = await queryRunner.query(query);

    await queryRunner.release();
    return result;
  } catch (error) {
    console.error("Query failed due to: " + error);
    throw error;
  }
}