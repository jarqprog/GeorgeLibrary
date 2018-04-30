package com.jarq.system.models.address;

import com.jarq.system.models.NullModel;

public class NullAddress extends NullModel implements IAddress {

    NullAddress() {}

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
    public String getPostalCode() {
        return getNotAvailable();
    }

    @Override
    public String getCity() {
        return getNotAvailable();
    }

    @Override
    public String getStreet() {
        return getNotAvailable();
    }

    @Override
    public String getHouseNo() {
        return getNotAvailable();
    }

    @Override
    public String getApartmentNo() {
        return getNotAvailable();
    }

    @Override
    public String toString() {
        return "NullAddress{" +
                "getNotAvailable()='" + getNotAvailable() + '\'' +
                '}';
    }
}
