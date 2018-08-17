package com.antman.extensionmarket42.repositories;

import com.antman.extensionmarket42.repositories.base.GenericRepository;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class GenericRepositoryImpl implements GenericRepository {
    private SessionFactory sessionFactory;

    @Autowired
    public GenericRepositoryImpl(SessionFactory sessionFactory){
        this.sessionFactory=sessionFactory;
    }
    @Override
    public List GetAll() {
        return null;
    }

    @Override
    public void add(Object entity) {

    }

    @Override
    public void remove(Object entity) {

    }

    @Override
    public void update(Object entity) {

    }

    @Override
    public Object getById(int id) {
        return null;
    }
}
