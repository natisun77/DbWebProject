package com.nataliia.dao;

import java.util.List;

public interface GenericDao<T> {

    void add(T object);

    T getById(Class<T> entityClazz, long id);

    List<T> getAll(Class<T> entityClass);

    void update(T object);

    void delete(T t);

    void deleteById(Class<T> entityClass, long id);
}
