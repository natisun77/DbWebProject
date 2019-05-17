package com.nataliia.dao;

import com.nataliia.model.Role;
import com.nataliia.utils.HibernateSessionFactoryUtil;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class RoleDaoHibImpl {

    public List<Role> getRoles() {
        List<Role> roles = (List<Role>)  HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession()
                .createQuery("From Role")
                .list();
        return roles;
    }

    public void addRole(Role role) {
        Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession();
        Transaction transaction = session.beginTransaction();
        session.save(role);
        transaction.commit();
        session.close();
    }
    public void updateRole(Role role) {
        Session session = HibernateSessionFactoryUtil
                .getSessionFactory()
                .openSession();
        Transaction tx1 = session.beginTransaction();
        session.update(role);
        tx1.commit();
        session.close();
    }
}
