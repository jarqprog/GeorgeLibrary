package com.jarq.system.models.user;

import com.jarq.system.models.NullModel;
import com.jarq.system.models.address.IAddress;
import com.jarq.system.models.address.IDaoAddress;
import com.jarq.system.models.repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public class NullUser extends NullModel implements IUser {
    
    private final IDaoAddress daoAddress;
    
    NullUser(IDaoAddress daoAddress) {
        this.daoAddress = daoAddress;
    }

    @Override
    public void setPassword(String password) {}

    @Override
    public String getPassword() {
        return getNotAvailable();
    }

    @Override
    public List<IRepository> getRepositories() {
        return new ArrayList<>();
    }

    @Override
    public void setName(String firstName) {

    }

    @Override
    public void setSurname(String lastName) {

    }

    @Override
    public void setEmail(String email) {

    }

    @Override
    public void setAddress(IAddress address) {

    }

    @Override
    public String getFullName() {
        return getNotAvailable();
    }

    @Override
    public String getName() {
        return getNotAvailable();
    }

    @Override
    public String getSurname() {
        return getNotAvailable();
    }

    @Override
    public String getEmail() {
        return getNotAvailable();
    }

    @Override
    public IAddress getAddress() {
        return daoAddress.createNullAddress();
    }

    @Override
    public String toString() {
        return "NullUser{" +
                "getNotAvailable()='" + getNotAvailable() + '\'' +
                '}';
    }
}
