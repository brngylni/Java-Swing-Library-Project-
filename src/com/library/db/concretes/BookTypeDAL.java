package com.library.db.concretes;

import com.library.db.abstracts.LibraryDAL;
import com.library.entities.abstracts.LibraryEntity;
import com.library.entities.concretes.BookType;

import java.sql.ResultSet;
import java.util.List;

public class BookTypeDAL implements LibraryDAL {
    private final DBHelper helper;
    public BookTypeDAL(String connectionString, String username, String password) {
        this.helper = new DBHelper(connectionString, username, password);
    }

    @Override
    public boolean add(LibraryEntity entity) {
        try{
            var bookType = (BookType) entity;
            String query = "INSERT INTO book_types(name) VALUES (?)";
            var statement = DBHelper.connection.prepareStatement(query);
            statement.setString(1, bookType.getName());
            var result = statement.executeUpdate();
            return result > 0;
        }catch (Exception exception){
            System.out.println(exception);
            return false;
        }
    }

    @Override
    public List<LibraryEntity> getAll() {
        return null;
    }

    @Override
    public boolean edit(LibraryEntity entity) {
        return false;
    }

    @Override
    public boolean delete(int id) {
        return false;
    }

    @Override
    public LibraryEntity get(int id) {
        try{
            String query = "SELECT * FROM book_types WHERE id=?";
            var statement = DBHelper.connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            results.next();
            String name = results.getString("name");
            return new BookType(id, name);
        }catch (Exception exception){
            return null;
        }
    }

    @Override
    public LibraryEntity getByName(String name) {
        try{
            String query = "SELECT * FROM book_types WHERE name=?";
            var statement = DBHelper.connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();
            results.next();
            int id = results.getInt("id");
            return new BookType(id, name);
        }catch (Exception exception){
            return null;
        }
    }
}
