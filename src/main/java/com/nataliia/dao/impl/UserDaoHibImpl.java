package com.nataliia.dao.impl;

import com.nataliia.dao.UserDao;
import com.nataliia.model.User;
import com.nataliia.utils.HashUtil;
import com.nataliia.utils.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

import java.util.Optional;

public class UserDaoHibImpl extends GenericDaoImpl<User> implements UserDao {
    private static final Logger logger = Logger.getLogger(UserDaoHibImpl.class);

    @Override
    public void add(User user) {
        user.setPassword(HashUtil.getSHA512SecurePassword(user.getPassword(), user.getSalt()));
        super.add(user);
    }

    @Override
    public Optional<User> getUserByName(String name) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("name", name));
            logger.debug("User with name " + name + " was found.");
            return Optional.ofNullable((User) criteria.uniqueResult());
        } catch (Exception e) {
            logger.error("User with name " + name + " was not found." , e);
            return Optional.empty();
        }
    }
}
