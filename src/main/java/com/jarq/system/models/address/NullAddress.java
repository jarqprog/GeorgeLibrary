package com.jarq.system.models.address;

public class NullAddress implements IAddress {

    private final String data = "n/a";
    private final int id = 0;

    @Override
    public void setId(int id) {

    }

    @Override
    public void setPostalCode(String postalCode) {

    }

    @Override
    public void setCity(String city) {

    }

    @Override
    public void setStreet(String street) {

    }

    @Override
    public void setHouseNo(String houseNo) {

    }

    @Override
    public void setApartmentNo(String apartmentNo) {

    }

    @Override
    public int getId() {
        return id;
    }

    @Override
    public String getPostalCode() {
        return data;
    }

    @Override
    public String getCity() {
        return data;
    }

    @Override
    public String getStreet() {
        return data;
    }

    @Override
    public String getHouseNo() {
        return data;
    }

    @Override
    public String getApartmentNo() {
        return data;
    }
}
