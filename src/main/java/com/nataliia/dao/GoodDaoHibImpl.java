package com.nataliia.dao;

import com.nataliia.model.Good;
import com.nataliia.utils.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.Collections;
import java.util.List;

public class GoodDaoHibImpl {
    private static final Logger LOGGER = Logger.getLogger(GoodDaoHibImpl.class);

    public List<Good> getGoods() {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            List<Good> goods = session
                    .createQuery("From Good")
                    .list();
            LOGGER.error("List of goods is shown");
            return goods;
        } catch (Exception e) {
            LOGGER.error("Can't find goods", e);
            return Collections.EMPTY_LIST;
        }
    }

    public void addGood(Good good) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(good);
            transaction.commit();
            LOGGER.debug("Good " + good.getName() + " was added");
        } catch (Exception e) {
            LOGGER.error("Can't add good", e);
        }
    }

    public Good findGoodById(long id) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            LOGGER.error("Good with ID " + id + " was found");
            return session.get(Good.class, id);
        } catch (Exception e) {
            LOGGER.error("Can't find good with ID " + id, e);
            return null;
        }
    }

    public void deleteGoodById(long id) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Good good = session.get(Good.class, id);
            deleteGood(good);
            LOGGER.error("Good with ID " + id + " was deleted");
        } catch (Exception e) {
            LOGGER.error("Can't delete good with ID " + id, e);
        }
    }

    public void deleteGood(Good good) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.delete(good);
            tx1.commit();
            LOGGER.error("Good with ID " + good.getId() + " was deleted");
        } catch (Exception e) {
            LOGGER.error("Can't delete good with ID " + good.getId(), e);
        }
    }

    public void updateGood(Good good) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.update(good);
            tx1.commit();
            LOGGER.error("Good with ID " + good.getId() + " was updated");
        } catch (Exception e) {
            LOGGER.error("Can't update good with ID " + good.getId(), e);
        }
    }
}
