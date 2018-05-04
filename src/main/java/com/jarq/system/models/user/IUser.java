package com.jarq.system.models.user;

import com.jarq.system.models.Identifiable;
import com.jarq.system.models.repository.IRepository;

import java.util.List;

public interface IUser extends IPerson, Identifiable {

    void setPassword(String password);

    String getPassword();
}
