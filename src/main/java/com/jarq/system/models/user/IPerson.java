package com.jarq.system.models.user;

import com.jarq.system.models.address.IAddress;

public interface IPerson {

    void setName(String firstName);


    void setSurname(String lastName);


    void setEmail(String email);

    void setAddress(IAddress address);

    String getFullName();

    int getId();

    String getName();

    String getSurname();

    String getEmail();

    IAddress getAddress();
}
