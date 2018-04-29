package com.jarq.system.models.user;

import com.jarq.system.models.address.IAddress;
import com.jarq.system.models.address.IDaoAddress;
import com.jarq.system.models.repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public class NullUser implements IUser {

    private final String notAvailable = "n/a";
    private final IDaoAddress daoAddress;


    NullUser(IDaoAddress daoAddress) {
        this.daoAddress = daoAddress;
    }

    @Override
    public void setPassword(String password) {}

    @Override
    public String getPassword() {
        return notAvailable;
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
        return notAvailable;
    }

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getName() {
        return notAvailable;
    }

    @Override
    public String getSurname() {
        return notAvailable;
    }

    @Override
    public String getEmail() {
        return notAvailable;
    }

    @Override
    public IAddress getAddress() {
        return daoAddress.createNullAddress();
    }

    @Override
    public String toString() {
        return "NullUser{" +
                "notAvailable='" + notAvailable + '\'' +
                '}';
    }
}
