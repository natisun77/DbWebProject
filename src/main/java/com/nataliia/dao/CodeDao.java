package com.nataliia.dao;

import com.nataliia.model.Code;

public interface CodeDao extends GenericDao<Code> {
    boolean isValidCode(String value, long userId);
}
