package com.jarq.system.models.address;

public class Address implements IAddress {

    private int id;
    private String postalCode;
    private String city;
    private String street;
    private String houseNo;
    private String apartmentNo;


    Address(String postalCode, String city, String street, String houseNo) {
        this.id = -1;
        this.postalCode = postalCode;
        this.city = city;
        this.street = street;
        this.houseNo = houseNo;
        this.apartmentNo = "not known";
    }


    Address(int id, String postalCode, String city, String street, String houseNo) {
        this(postalCode, city, street, houseNo);
        this.id = id;
    }

    @Override
    public void setId(int id) {
        this.id = id;
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
    public int getId() {
        return id;
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
}
