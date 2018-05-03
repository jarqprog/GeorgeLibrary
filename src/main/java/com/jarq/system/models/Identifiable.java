package com.jarq.system.models;

import com.jarq.system.dao.Dao;

public interface Identifiable extends Dao {

    int getId();
    void setId(int id);
}
