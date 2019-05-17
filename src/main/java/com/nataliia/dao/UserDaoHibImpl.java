package com.nataliia.dao;

import com.nataliia.model.User;
import com.nataliia.utils.HashUtil;
import com.nataliia.utils.HibernateSessionFactoryUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.hibernate.criterion.Restrictions;

import java.util.List;

public class UserDaoHibImpl {

    public List<User> getUsers() {
        List<User> users = (List<User>)  HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("From User")
                .list();
        return users;
    }

    public void addUser(User user) {
        Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession();
        Transaction transaction = session.beginTransaction();
        user.setPassword(HashUtil.getSHA512SecurePassword(user.getPassword(), user.getSalt()));
        session.save(user);
        transaction.commit();
        session.close();
    }

    public User findUserById(long id) {
        return HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .get(User.class, id);
    }

    public User findUserByName(String name) {
        Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession();
        Criteria criteria = session.createCriteria(User.class);
        criteria.add(Restrictions.eq("name", name));
        return (User) criteria.uniqueResult();
    }

    public void deleteUserById(long id) {
        User user = HibernateSessionFactoryUtil.getSessionFactory().openSession().get(User.class, id);
        deleteUser(user);
    }

    public void deleteUser(User user) {
        Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession();
        Transaction tx1 = session.beginTransaction();
        session.delete(user);
        tx1.commit();
        session.close();
    }

    public void updateUser(User user) {
        Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(user);
        tx1.commit();
        session.close();
    }
}
