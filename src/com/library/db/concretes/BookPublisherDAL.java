package com.library.db.concretes;

import com.library.db.abstracts.LibraryDAL;
import com.library.entities.abstracts.LibraryEntity;
import com.library.entities.concretes.BookPublisher;
import com.library.entities.concretes.BookType;

import java.sql.ResultSet;
import java.util.List;

public class BookPublisherDAL implements LibraryDAL {
    private final DBHelper helper;

    public BookPublisherDAL(String connectionString, String username, String password) {
        this.helper = new DBHelper(connectionString, username, password);
    }

    @Override
    public boolean add(LibraryEntity entity) {
        try{
            var bookPublisher = (BookPublisher) entity;
            String query = "INSERT INTO publishers(name) VALUES (?)";
            var statement = DBHelper.connection.prepareStatement(query);
            statement.setString(1, bookPublisher.getName());
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
            String query = "SELECT * FROM publishers WHERE id=?";
            var statement = DBHelper.connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            results.next();
            String name = results.getString("name");
            return new BookPublisher(id, name);
        }catch (Exception exception){
            return null;
        }
    }

    @Override
    public LibraryEntity getByName(String name) {
        try{
            String query = "SELECT * FROM publishers WHERE name=?";
            var statement = DBHelper.connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();
            results.next();
            int id = results.getInt("id");
            return new BookPublisher(id, name);
        }catch (Exception exception){
            return null;
        }
    }
}
