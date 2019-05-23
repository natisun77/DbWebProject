package com.nataliia.dao.impl;

import com.nataliia.dao.UserDao;
import com.nataliia.model.User;
import com.nataliia.utils.HashUtil;
import com.nataliia.utils.HibernateSessionFactoryUtil;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;

public class UserDaoHibImpl extends GenericDaoImpl<User> implements UserDao {

    @Override
    public void add(User user) {
        user.setPassword(HashUtil.getSHA512SecurePassword(user.getPassword(), user.getSalt()));
        super.add(user);
    }

    @Override
    public User getUserByName(String name) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Criteria criteria = session.createCriteria(User.class);
            criteria.add(Restrictions.eq("name", name));
            return (User) criteria.uniqueResult();
        } catch (Exception e) {
            return null;
        }
    }
}
