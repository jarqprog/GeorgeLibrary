package com.jarq.system.models.user;

import com.jarq.system.models.repository.IRepository;

import java.util.List;

public interface IUser extends IPerson {

    void setPassword(String password);

    String getPassword();

    List<IRepository> getRepositories();

}
