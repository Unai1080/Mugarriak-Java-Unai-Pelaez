package Connection;

import java.sql.*;

public class DBkonexioa {

    private Connection conexion;

    private String url = "jdbc:mysql://10.14.3.2/programazioa";
    private final static String usuario = "unai";
    private final static String contraseña = "unai";

    // Constructor de la clase
    public DBkonexioa() throws SQLException {
        try {
            // Cargar el controlador de MySQL JDBC
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Establecer la conexión con la base de datos MySQL
            conexion = DriverManager.getConnection(url, usuario, contraseña);
            System.out.println("Conexión establecida con éxito");

        } catch (ClassNotFoundException | SQLException e) {
            System.out.println("Error al establecer la conexión: " + e.getMessage());
            throw new SQLException("Error al establecer la conexión: " + e.getMessage());
        }
    }


    public ResultSet select(String consulta) throws SQLException {
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            statement = this.conexion.createStatement();
            resultSet = statement.executeQuery(consulta);
        } catch (SQLException e) {
            System.out.println("Error al ejecutar la consulta: " + e.getMessage());
            throw e;
        } finally {
            System.out.println("Select correcto");
        }
        return resultSet;
    }

    public void update(String query) {
        Statement statement = null;
        try {
            statement = conexion.createStatement();
            int rows = statement.executeUpdate(query);
            System.out.println(rows + " rows updated");
        } catch (SQLException e) {
            throw new RuntimeException("Error executing update: " + e.getMessage(), e);
        } finally {
            try {
                if (statement != null) {
                    statement.close();
                }
                if (conexion != null) {
                    conexion.close();
                }
            } catch (SQLException e) {
                throw new RuntimeException("Error closing resources: " + e.getMessage(), e);
            }
        }
    }


    public void cerrar()  {
        try {
            this.conexion.close();
        } catch (SQLException e) {
            System.out.println("Errorea konexioa ixterakoan");;
        }
        System.out.println("Konexioa itxita");
    }
}