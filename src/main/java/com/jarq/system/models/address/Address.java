package com.jarq.system.models.address;

import com.jarq.system.models.Model;

public class Address extends Model implements IAddress {

    private String postalCode;
    private String city;
    private String street;
    private String houseNo;
    private String apartmentNo = "-";
    private final int userId;

    Address(int id, String postalCode, String city, String street, String houseNo, int userId) {
        setId(id);
        this.postalCode = postalCode;
        this.city = city;
        this.street = street;
        this.houseNo = houseNo;
        this.userId = userId;
    }

    @Override
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Override
    public void setCity(String city) {
        this.city = city;
    }

    @Override
    public void setStreet(String street) {
        this.street = street;
    }

    @Override
    public void setHouseNo(String houseNo) {
        this.houseNo = houseNo;
    }

    @Override
    public void setApartmentNo(String apartmentNo) {
        this.apartmentNo = apartmentNo;
    }

    @Override
    public String getPostalCode() {
        return postalCode;
    }

    @Override
    public String getCity() {
        return city;
    }

    @Override
    public String getStreet() {
        return street;
    }

    @Override
    public String getHouseNo() {
        return houseNo;
    }

    @Override
    public String getApartmentNo() {
        return apartmentNo;
    }

    @Override
    public int getUserId() {
        return userId;
    }

    @Override
    public String toString() {
        return "Address{" +
                "postalCode='" + postalCode + '\'' +
                ", city='" + city + '\'' +
                ", street='" + street + '\'' +
                ", houseNo='" + houseNo + '\'' +
                ", apartmentNo='" + apartmentNo + '\'' +
                ", userId=" + userId +
                "} " + super.toString();
    }
}
