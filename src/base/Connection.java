package base;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author kevindanielguzmen98@gmail.com
 * @version 0.0.1
 * @since 2018-04-29
 */
public class Connection {

    private static java.sql.Connection connectionInstance = null;

    /**
     * Provee una instancia de la conexion a la base de datos
     *
     * @return java.sql.Connection conexion
     */
    public static java.sql.Connection openConnection() {
        try {
            if (Connection.connectionInstance == null) {
                Class.forName("com.mysql.jdbc.Driver");
                Connection.connectionInstance = DriverManager.getConnection("jdbc:mysql://localhost/adminitracion_facturas", "root", null);
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(Connection.class.getName()).log(Level.SEVERE, null, ex);
        }
        return Connection.connectionInstance;
    }

    /**
     * Cierra la conexion a la base de datos
     *
     * @throws java.sql.SQLException
     */
    public static void closeConnection() throws SQLException {
        if (Connection.connectionInstance != null) {
            Connection.connectionInstance.close();
        }
    }
}
