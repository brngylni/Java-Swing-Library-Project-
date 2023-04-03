package com.library.db.concretes;

import com.library.db.abstracts.LibraryDAL;
import com.library.entities.abstracts.LibraryEntity;
import com.library.entities.concretes.Book;
import com.library.entities.concretes.BookPublisher;
import com.library.entities.concretes.BookType;
import com.library.entities.concretes.BookWriter;

import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;


public class BookDAL implements LibraryDAL {

    // TO DO: Should the helper object be static and single object?
    private final DBHelper helper;
    private final String connectionString, username, password;

    public BookDAL(String connectionString, String username, String password){
        this.helper = new DBHelper(connectionString, username, password);
        this.connectionString = connectionString;
        this.username = username;
        this.password = password;
    }

    // Function to add books.
    @Override
    public boolean add(LibraryEntity entity) {
        var book = (Book) entity;
        try{
            String query = "INSERT INTO books(book_name, book_writer, book_publisher, book_type) VALUES(?, ?, ?, ?)";
            var statement = DBHelper.connection.prepareStatement(query);
            statement.setString(1, book.getBookName());
            statement.setInt(2, book.getBookWriter().getId());
            statement.setInt(3, book.getBookPublisher().getId());
            statement.setInt(4, book.getBookType().getId());
            int row = statement.executeUpdate();
            return row > 0;
        }catch (Exception exception){
            return false;
        }
    }

    // Function to get all Books
    @Override
    public List<LibraryEntity> getAll() {
        try{
            BookTypeDAL typeDAL = new BookTypeDAL(this.connectionString, this.username, this.password);
            BookWriterDAL writerDAL = new BookWriterDAL(this.connectionString, this.username, this.password);
            BookPublisherDAL publisherDAL = new BookPublisherDAL(this.connectionString, this.username, this.password);
            List<LibraryEntity> lst = new ArrayList<>();
            String query = "SELECT * FROM books";
            var statement = DBHelper.connection.prepareStatement(query);
            ResultSet results = statement.executeQuery();
            while(results.next()){
                int id = results.getInt("id");
                String name = results.getString("book_name");
                BookType bookType = (BookType) typeDAL.get(results.getInt("book_type"));
                BookWriter bookWriter = (BookWriter) writerDAL.get(results.getInt("book_writer"));
                BookPublisher bookPublisher = (BookPublisher) publisherDAL.get(results.getInt("book_publisher"));
                lst.add(new Book(id, name, bookWriter, bookPublisher, bookType));
            }
            return lst;
        }catch (Exception exception){
            System.out.println(exception);
            return null;
        }
    }

    @Override
    public boolean edit(LibraryEntity entity) {
        try {
            var book = (Book) entity;
            String query = "UPDATE books SET book_name=?, book_writer=?, book_type=?, book_publisher=? WHERE id=?";
            var statement = DBHelper.connection.prepareStatement(query);
            statement.setString(1, book.getBookName());
            statement.setInt(2, book.getBookWriter().getId());
            statement.setInt(3, book.getBookType().getId());
            statement.setInt(4, book.getBookPublisher().getId());
            statement.setInt(5, book.getId());
            var result = statement.executeUpdate();
            return result > 0;
        }catch (Exception exception){
            System.out.println(exception);
            return false;
        }
    }

    @Override
    public boolean delete(int id) {
        try {
            String query = "DELETE FROM books WHERE id=?";
            var statement = DBHelper.connection.prepareStatement(query);
            statement.setInt(1, id);
            var result = statement.executeUpdate();
            return result > 0;
        }catch (Exception exception){
            return false;
        }
    }

    @Override
    public LibraryEntity get(int id) {
        try{
            BookTypeDAL typeDAL = new BookTypeDAL(this.connectionString, this.username, this.password);
            BookWriterDAL writerDAL = new BookWriterDAL(this.connectionString, this.username, this.password);
            BookPublisherDAL publisherDAL = new BookPublisherDAL(this.connectionString, this.username, this.password);
            String query = "SELECT * FROM books WHERE id=?";
            var statement = DBHelper.connection.prepareStatement(query);
            statement.setInt(1, id);
            ResultSet results = statement.executeQuery();
            results.next();
            String name = results.getString("book_name");
            BookType bookType = (BookType) typeDAL.get(results.getInt("book_type"));
            BookWriter bookWriter = (BookWriter) writerDAL.get(results.getInt("book_writer"));
            BookPublisher bookPublisher = (BookPublisher) publisherDAL.get(results.getInt("book_publisher"));
            return new Book(id, name, bookWriter, bookPublisher, bookType);
        }catch (Exception exception){
            return null;
        }
    }

    @Override
    public LibraryEntity getByName(String name) {
        try{
            BookTypeDAL typeDAL = new BookTypeDAL(this.connectionString, this.username, this.password);
            BookWriterDAL writerDAL = new BookWriterDAL(this.connectionString, this.username, this.password);
            BookPublisherDAL publisherDAL = new BookPublisherDAL(this.connectionString, this.username, this.password);
            String query = "SELECT * FROM books WHERE book_name=?";
            var statement = DBHelper.connection.prepareStatement(query);
            statement.setString(1, name);
            ResultSet results = statement.executeQuery();
            results.next();
            int id = results.getInt("id");
            BookType bookType = (BookType) typeDAL.get(results.getInt("book_type"));
            BookWriter bookWriter = (BookWriter) writerDAL.get(results.getInt("book_writer"));
            BookPublisher bookPublisher = (BookPublisher) publisherDAL.get(results.getInt("book_publisher"));
            return new Book(id, name, bookWriter, bookPublisher, bookType);
        }catch (Exception exception){
            return null;
        }
    }


}
