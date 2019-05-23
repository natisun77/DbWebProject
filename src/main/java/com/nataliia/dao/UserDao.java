package com.nataliia.dao;

import com.nataliia.model.User;

public interface UserDao extends GenericDao<User> {

    User getUserByName(String name);
}
