package com.antman.extensionmarket42.repositories.base;

import java.util.List;

public interface GenericRepository<T> {
    List<T> GetAll();
    void add(T entity);
    void remove(T entity);
    void update(T entity);
    T getById(int id);

}
