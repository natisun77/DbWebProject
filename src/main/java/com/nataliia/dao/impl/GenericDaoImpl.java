package com.nataliia.dao.impl;

import com.nataliia.dao.GenericDao;
import com.nataliia.utils.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public abstract class GenericDaoImpl<T> implements GenericDao<T> {
    private static final Logger logger = Logger.getLogger(GenericDaoImpl.class);


    @Override
    public void add(T object) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            logger.debug("Object " + object.getClass() + " was added.");
            session.save(object);
            transaction.commit();
        } catch (Exception e) {
            logger.error("Object " + object.getClass() + " was not added" , e);
        }
    }

    @Override
    public T getById(Class<T> entityClazz, final long id) {
        logger.debug("Object of" + entityClazz + " with id " + id + " was found.");
        return HibernateSessionFactoryUtil.getSessionFactory()
                .openSession()
                .get(entityClazz, id);
    }

    @Override
    public List<T> getAll(Class<T> entityClass) {
        logger.debug("Objects of " + entityClass + "were found.");
        return HibernateSessionFactoryUtil.getSessionFactory().
                openSession().
                createCriteria(entityClass).
                list();
    }

    @Override
    public void update(T object) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.update(object);
            transaction.commit();
            logger.debug("Objects of " + object.getClass() + " was updated.");
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
            logger.debug("Objects of " + object.getClass() + "was deleted.");
        }
    }

    @Override
    public void deleteById(Class<T> entityClass, long id) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            T t = session.get(entityClass, id);
            delete(t);
            logger.debug("Object of" + entityClass + " with id " + id + " was deleted.");
        }
    }
}
