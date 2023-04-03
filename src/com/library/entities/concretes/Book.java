package com.library.entities.concretes;

import com.library.entities.abstracts.LibraryEntity;

import java.lang.reflect.Array;

public class Book implements LibraryEntity {
    private int id;
    private String bookName;
    private BookWriter bookWriter;
    private BookPublisher bookPublisher;
    private BookType bookType;

    public Book(int id, String bookName, BookWriter bookWriter, BookPublisher bookPublisher, BookType bookType) {
        this.id = id;
        this.bookName = bookName;
        this.bookWriter = bookWriter;
        this.bookPublisher = bookPublisher;
        this.bookType = bookType;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public BookWriter getBookWriter() {
        return bookWriter;
    }

    public void setBookWriter(BookWriter bookWriter) {
        this.bookWriter = bookWriter;
    }

    public BookPublisher getBookPublisher() {
        return bookPublisher;
    }

    public void setBookPublisher(BookPublisher bookPublisher) {
        this.bookPublisher = bookPublisher;
    }

    public BookType getBookType() {
        return bookType;
    }

    public void setBookType(BookType bookType) {
        this.bookType = bookType;
    }

    public String[] asString(){
        return new String[]{String.valueOf(this.id), this.bookName
                , this.bookWriter.getName(), this.bookPublisher.getName(), this.bookType.getName()};
    }



}
