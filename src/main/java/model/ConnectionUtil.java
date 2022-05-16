package model;


import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


public class ConnectionUtil {//класс подключения к бд
    Connection conn = null;

    public static Connection conDB() {
        try {
            Connection con = DriverManager.getConnection("jdbc:postgresql://localhost:5432/DB_SK", "postgres", "42a98w06");
            return con;
        } catch (SQLException ex) {
            System.err.println("ConnectionUtil : " + ex.getMessage());
            return null;
        }
    }

}

