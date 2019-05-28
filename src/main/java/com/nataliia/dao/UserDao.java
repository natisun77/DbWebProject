package com.nataliia.dao;

import com.nataliia.model.User;

import java.util.Optional;

public interface UserDao extends GenericDao<User> {

    Optional<User> getUserByName(String name);
}
