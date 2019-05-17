package com.nataliia.dao;

import com.nataliia.model.Good;
import com.nataliia.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class GoodDaoHibImpl {
    public List<Good> getGoods() {
        List<Good> goods = (List<Good>)  HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("From Good")
                .list();
        return goods;
    }

    public void addGood(Good good) {
        Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession();
        Transaction transaction = session.beginTransaction();
        session.save(good);
        transaction.commit();
        session.close();
    }

    public Good findGoodById(long id) {
        return HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .get(Good.class, id);
    }

    public void deleteGoodById(long id) {
        Good good = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .get(Good.class, id);
        deleteGood(good);
    }

    public void deleteGood(Good good) {
        Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(good);
        tx1.commit();
        session.close();
    }

    public void updateGood(Good good) {
        Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(good);
        tx1.commit();
        session.close();
    }
}
