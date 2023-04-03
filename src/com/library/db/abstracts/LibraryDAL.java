package com.library.db.abstracts;

import com.library.entities.abstracts.LibraryEntity;

import java.util.List;

public interface LibraryDAL {
    boolean add(LibraryEntity entity);
    List<LibraryEntity> getAll();
    boolean edit(LibraryEntity entity);
    boolean delete(int id);

    LibraryEntity get(int id);
    LibraryEntity getByName(String name);

}
