package com.nataliia.dao;

import com.nataliia.model.User;
import com.nataliia.utils.HashUtil;
import com.nataliia.utils.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.Collections;
import java.util.List;

public class UserDaoHibImpl {
    private static final Logger LOGGER = Logger.getLogger(GoodDaoHibImpl.class);

    public List<User> getUsers() {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            List<User> users = session
                    .createQuery("From User")
                    .list();
            LOGGER.error("List of users is shown");
            return users;
        } catch (Exception e) {
            LOGGER.error("Can't find users", e);
            return Collections.emptyList();
        }
    }

    public void addUser(User user) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            user.setPassword(HashUtil.getSHA512SecurePassword(user.getPassword(), user.getSalt()));
            session.save(user);
            transaction.commit();
            LOGGER.error("Good " + user.getName() + " was added");
        } catch (Exception e) {
            LOGGER.error("Can't add user", e);
        }
    }

    public User findUserById(long id) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            LOGGER.error("User with ID " + id + " was found");
            return session.get(User.class, id);
        } catch (Exception e) {
            LOGGER.error("Can't find user with ID " + id, e);
            return null;
        }
    }

    public User findUserByName(String name) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("name", name));
            LOGGER.error("User with name " + name + " was found");
            return (User) criteria.uniqueResult();
        } catch (Exception e) {
            LOGGER.error("Can't find user with name " + name, e);
            return null;
        }
    }

    public void deleteUserById(long id) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            User user = session.get(User.class, id);
            deleteUser(user);
            LOGGER.error("User with ID " + id + " was deleted");
        } catch (Exception e) {
            LOGGER.error("Can't delete user with ID " + id, e);
        }
    }

    public void deleteUser(User user) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.delete(user);
            tx1.commit();
            LOGGER.error("Good with ID " + user.getId() + " was deleted");
        } catch (Exception e) {
            LOGGER.error("Can't delete good with ID " + user.getId(), e);
        }
    }

    public void updateUser(User user) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.update(user);
            tx1.commit();
            LOGGER.error("User with ID " + user.getId() + " was updated");
        } catch (Exception e) {
            LOGGER.error("Can't update user with ID " + user .getId(), e);
        }
    }
}
