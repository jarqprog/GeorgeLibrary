package com.jarq.system.models.address;

public class NullAddress implements IAddress {

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
        return 0;
    }

    @Override
    public String getPostalCode() {
        return "";
    }

    @Override
    public String getCity() {
        return "";
    }

    @Override
    public String getStreet() {
        return "";
    }

    @Override
    public String getHouseNo() {
        return "";
    }

    @Override
    public String getApartmentNo() {
        return "";
    }
}
