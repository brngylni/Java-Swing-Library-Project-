package com.library.db.concretes;

import java.sql.Connection;
import java.sql.DriverManager;

class DBHelper {
    // Having one connection object instead of re-initializing it for each LibraryDAL instance.
    static Connection connection;

    public DBHelper(String connectionString, String username, String password){
        if(connection == null){
            connection = connect(connectionString, username, password);
        }
    }

    private Connection connect(String connectionString, String username, String password){
        Connection con = null;
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection(connectionString, username, password);
            return con;
        }catch (Exception exception){
            System.out.println(exception);
            return null;
        }
    }
}
