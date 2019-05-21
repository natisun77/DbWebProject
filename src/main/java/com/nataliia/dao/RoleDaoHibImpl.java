package com.nataliia.dao;

import com.nataliia.model.Role;
import com.nataliia.utils.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.util.List;

public class RoleDaoHibImpl {

    private static final Logger LOGGER = Logger.getLogger(RoleDaoHibImpl.class);

    public void addRole(Role role) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(role);
            transaction.commit();
            LOGGER.debug("Role " + role.getName() + " was added");
        } catch (Exception e){
            LOGGER.error("Can't add role", e);
        }
    }
    public void updateRole(Role role) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction tx1 = session.beginTransaction();
            session.update(role);
            tx1.commit();
            LOGGER.debug("Role with ID " + role.getId() + " was updated");
        } catch (Exception e) {
            LOGGER.error("Can't update role with ID " + role.getId(), e);
        }
    }
}
