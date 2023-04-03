package com.library.db.concretes;

import com.library.db.abstracts.LibraryDAL;
import com.library.entities.abstracts.LibraryEntity;
import com.library.entities.concretes.BookWriter;
import com.library.entities.concretes.User;

import java.sql.ResultSet;
import java.util.List;

public class UserDAL{
    private final DBHelper helper;

    public UserDAL(String connectionString, String username, String password) {
        this.helper = new DBHelper(connectionString, username, password);
    }

    public boolean exists(String username, String password){
        try{
            String query = "SELECT COUNT(*) as count FROM users WHERE username=? and password=?";
            var statement = DBHelper.connection.prepareStatement(query);
            statement.setString(1, username);
            statement.setString(2, password);
            ResultSet results = statement.executeQuery();
            results.next();
            int count = results.getInt("count");
            return count > 0;
        }catch (Exception exception){
            System.out.println(exception);
            return false;
        }
    }


}
