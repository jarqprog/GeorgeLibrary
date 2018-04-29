package com.jarq.system.models.address;

public interface IAddress {


    void setId(int id);

    void setPostalCode(String postalCode);

    void setCity(String city);

    void setStreet(String street);

    void setHouseNo(String houseNo);

    void setApartmentNo(String apartmentNo);

    int getId();

    String getPostalCode();

    String getCity();

    String getStreet();

    String getHouseNo();

    String getApartmentNo();
}
