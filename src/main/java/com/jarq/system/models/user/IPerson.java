package com.jarq.system.models.user;

import com.jarq.system.models.Identifiable;
import com.jarq.system.models.address.IAddress;

public interface IPerson extends Identifiable {

    void setName(String firstName);

    void setSurname(String lastName);

    void setEmail(String email);

    void setAddress(IAddress address);

    String getFullName();

    String getName();

    String getSurname();

    String getEmail();

    IAddress getAddress();
}
