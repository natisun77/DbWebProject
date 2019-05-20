package com.nataliia.dao;

import com.nataliia.model.Code;
import com.nataliia.utils.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import java.math.BigInteger;

public class CodeDaoHibImpl {
    private static final Logger LOGGER = Logger.getLogger(CodeDaoHibImpl.class);

    public void addCode(Code code) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil.getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            Transaction transaction = session.beginTransaction();
            session.save(code);
            transaction.commit();
        } catch (Exception e) {
            LOGGER.error("Can't add a code", e);
        }
    }

    public boolean isValidCode(String value, long userId, long goodId) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String sql = "SELECT EXISTS(SELECT * FROM confirmation_code " +
                    "WHERE user_id = ? and good_id = ? and value_code = ? " +
                    " and timestampdiff (minute, creation_date, now()) <1)";
            LOGGER.debug(sql);
            SQLQuery sqlQuery = session.createSQLQuery(sql);
            sqlQuery.setLong(0, userId);
            sqlQuery.setLong(1, goodId);
            sqlQuery.setString(2, value);
            return  ((BigInteger) sqlQuery.uniqueResult()).compareTo(BigInteger.ZERO) > 0;
        } catch (Exception e) {
            LOGGER.error("Code is not valid", e);
            return false;
        }
    }
}
