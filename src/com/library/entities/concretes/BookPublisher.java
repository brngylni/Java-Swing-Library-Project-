package com.library.entities.concretes;

import com.library.entities.abstracts.LibraryEntity;

public class BookPublisher implements LibraryEntity {

    private int id;
    private String name;

    public BookPublisher(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
