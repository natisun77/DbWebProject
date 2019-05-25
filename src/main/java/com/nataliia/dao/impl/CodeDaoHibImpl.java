package com.nataliia.dao.impl;

import com.nataliia.dao.CodeDao;
import com.nataliia.model.Code;
import com.nataliia.utils.HibernateSessionFactoryUtil;
import org.apache.log4j.Logger;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.math.BigInteger;

public class CodeDaoHibImpl extends GenericDaoImpl<Code> implements CodeDao {
    private static final Logger logger = Logger.getLogger(CodeDaoHibImpl.class);

    @Override
    public boolean isValidCode(String value, long userId) {
        SessionFactory sessionFactory = HibernateSessionFactoryUtil
                .getSessionFactory();
        try (Session session = sessionFactory.openSession()) {
            String sql = "SELECT EXISTS(SELECT * FROM confirmation_code " +
                    "WHERE user_id = ? and value_code = ? " +
                    " and timestampdiff (minute, creation_date, now()) <1)";
            logger.debug(sql);
            SQLQuery sqlQuery = session.createSQLQuery(sql);
            sqlQuery.setLong(0, userId);
            sqlQuery.setString(1, value);
            return  ((BigInteger) sqlQuery.uniqueResult()).compareTo(BigInteger.ZERO) > 0;
        } catch (Exception e) {
            logger.error("Code is not valid", e);
            return false;
        }
    }
}
