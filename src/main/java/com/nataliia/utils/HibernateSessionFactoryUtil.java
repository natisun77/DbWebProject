package com.nataliia.utils;

import com.nataliia.model.Code;
import com.nataliia.model.Good;
import com.nataliia.model.Order;
import com.nataliia.model.Role;
import com.nataliia.model.User;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;

public class HibernateSessionFactoryUtil {
    private static SessionFactory sessionFactory;
    private static final Logger LOGGER = Logger.getLogger(HibernateSessionFactoryUtil.class);

    private HibernateSessionFactoryUtil() {
    }

    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {
            try {
                Configuration configuration = new Configuration().configure();
                configuration.addAnnotatedClass(Role.class);
                configuration.addAnnotatedClass(User.class);
                configuration.addAnnotatedClass(Good.class);
                configuration.addAnnotatedClass(Code.class);
                configuration.addAnnotatedClass(Order.class);
                StandardServiceRegistryBuilder builder = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties());
                sessionFactory = configuration.buildSessionFactory(builder.build());

            } catch (Exception e) {
                LOGGER.error("Can't make sessionFactory", e);;
            }
        }
        return sessionFactory;
    }
}