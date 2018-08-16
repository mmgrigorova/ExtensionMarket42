package com.antman.extensionmarket42.repositories;

import java.util.List;

public interface GenericRepository<T> {
    List<T> GetAll();
    void add(T entity);
    void remove(T entity);
    void update(T entity);
    <T> T getById(int id);
}
