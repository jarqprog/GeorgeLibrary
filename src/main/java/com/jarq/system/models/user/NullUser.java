package com.jarq.system.models.user;

import com.jarq.system.models.NullModel;

public class NullUser extends NullModel implements IUser {
    
    NullUser() {}

    @Override
    public void setPassword(String password) {}

    @Override
    public String getPassword() {
        return getNotAvailable();
    }

    @Override
    public void setName(String firstName) {}

    @Override
    public void setSurname(String lastName) {}

    @Override
    public void setEmail(String email) {}

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
    public String toString() {
        return "NullUser{} " + super.toString();
    }
}
