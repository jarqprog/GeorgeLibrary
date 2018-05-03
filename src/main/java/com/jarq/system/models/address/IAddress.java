package com.jarq.system.models.address;

import com.jarq.system.models.Identifiable;

public interface IAddress extends Identifiable {

    void setPostalCode(String postalCode);

    void setCity(String city);

    void setStreet(String street);

    void setHouseNo(String houseNo);

    void setApartmentNo(String apartmentNo);

    String getPostalCode();

    String getCity();

    String getStreet();

    String getHouseNo();

    String getApartmentNo();

    int getUserId();
}
