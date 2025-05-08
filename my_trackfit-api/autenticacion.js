import { execute } from "../config/db.config";
import { TYPES } from "mssql";
import { useMock } from "../app";
import ErrorHandler from "../utils/ErrorHandler";

class AuthService {
    //Inicia sesión de un usuario usando sus credenciales.
  async loginUser(credentials) {
    const {
      Username: username,
      Password: password,
    } = credentials;

     // Define los parámetros de entrada para el procedimiento almacenado
    const params = {
      inUsername: [username, TYPES.VarChar],
      inPassword: [password, TYPES.VarChar],
    };

    try {
      if (useMock) {
        return { success: true, Id: 1, Username: "test" };
      } else {
        // Ejecuta el procedimiento almacenado 'sp_login'
        const response = await execute("sp_login", params, {});
        if (response.output.outResultCode == 0) {
          let data = response.recordset[0];
          const loginResponse = {
            success: true,
            Id: data.Id,
            Username: username,
          };
          return loginResponse;
        } else {
          return ErrorHandler(response);
        }
      }
    } catch (error) {
      console.error("Error details:", error);
      throw new Error(`An error occurred while logging in: ${error}`);
    }
  }
  //Cierra la sesión de un usuario dado su ID.
  async logoutUser(userId) {
    if (!userId) {
      return false;
    }

    const params = {
      inUserId: [String(userId), TYPES.Int],
    };

    try {
      if (useMock) {
        return true;
      } else {
        // Ejecuta el procedimiento almacenado 'sp_logout'
        const response = await execute("sp_logout", params, {});
        if (response.output.outResultCode == 0) {
          return true;
        } else {
          throw new Error("DB error");
        }
      }
    } catch (error) {
      console.error("Error details:", error);
      throw new Error(`An error occurred while logging out: ${error}`);
    }
  }
}

export default new AuthService();