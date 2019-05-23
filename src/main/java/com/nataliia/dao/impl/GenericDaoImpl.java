package com.nataliia.dao.impl;

import com.nataliia.dao.GenericDao;
import com.nataliia.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public abstract class GenericDaoImpl<T> implements GenericDao<T> {

    @Override
    public void add(T object) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(object);
            transaction.commit();
        } catch (Exception e) {
        }
    }

    @Override
    public T getById(Class<T> entityClazz, final long id) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().get(entityClazz, id);
    }

    @Override
    public List<T> getAll(Class<T> entityClass) {
        return HibernateSessionFactoryUtil.getSessionFactory().openSession().createCriteria(entityClass).list();
    }

    @Override
    public void update(T object) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.update(object);
            tx1.commit();
        }
    }

    @Override
    public void delete(T object) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.delete(object);
            transaction.commit();
        }
    }

    @Override
    public void deleteById(Class<T> entityClass, long id) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            T t = session.get(entityClass, id);
            delete(t);
        }
    }
}
