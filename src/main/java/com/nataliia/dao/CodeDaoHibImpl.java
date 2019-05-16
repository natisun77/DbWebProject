package com.nataliia.dao;

import com.nataliia.model.Code;
import com.nataliia.utils.HibernateSessionFactoryUtil;
import org.hibernate.SQLQuery;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.util.List;

public class CodeDaoHibImpl {

    public void addCode(Code code) {
        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        session.save(code);
        transaction.commit();
        session.close();
    }

    public boolean isValidCode(String value, long userId, long goodId) {

        Session session = HibernateSessionFactoryUtil.getSessionFactory().openSession();
        Transaction transaction = session.beginTransaction();
        String sql = "SELECT EXISTS(SELECT * FROM confirmation_code WHERE user_id = ? and good_id = ? and value_code = ? " +
                " and timestampdiff (minute, creation_date, now()) <1)";
        SQLQuery sqlQuery = session.createSQLQuery(sql);
        sqlQuery.setLong(1, userId);
        sqlQuery.setLong(2, goodId);
        sqlQuery.setString(3, value);
        List<Boolean> list = sqlQuery.list();
        transaction.commit();
        session.close();
        return (Boolean) list.get(0);
    }
}
