package com.jarq.system.models.address;

public class NullAddress implements IAddress {

    private final String notAvailable = "n/a";

    NullAddress() {}

    @Override
    public void setId(int id) {}

    @Override
    public void setPostalCode(String postalCode) {}

    @Override
    public void setCity(String city) {}

    @Override
    public void setStreet(String street) {}

    @Override
    public void setHouseNo(String houseNo) {}

    @Override
    public void setApartmentNo(String apartmentNo) {}

    @Override
    public int getId() {
        return 0;
    }

    @Override
    public String getPostalCode() {
        return notAvailable;
    }

    @Override
    public String getCity() {
        return notAvailable;
    }

    @Override
    public String getStreet() {
        return notAvailable;
    }

    @Override
    public String getHouseNo() {
        return notAvailable;
    }

    @Override
    public String getApartmentNo() {
        return notAvailable;
    }

    @Override
    public String toString() {
        return "NullAddress{" +
                "notAvailable='" + notAvailable + '\'' +
                '}';
    }
}
