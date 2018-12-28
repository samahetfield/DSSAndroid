package com.example.sergio.dssandroid.servidor;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Db {
    String url = "jdbc:mysql://localhost:3306/consorcio";
    String username = "root";
    String password = "";

    private Connection connection;

    public Db() throws SQLException, InstantiationException, IllegalAccessException, ClassNotFoundException {

        Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
        try {
            this.connection = DriverManager.getConnection(url, username,password);
        }catch(SQLException ex) {
            System.out.println("SQLException: " + ex.getMessage());
            System.out.println("SQLState: " + ex.getSQLState());
            System.out.println("VendorError: " + ex.getErrorCode());
        }

    }
    public Connection getDB() {
        return this.connection;
    }
}
