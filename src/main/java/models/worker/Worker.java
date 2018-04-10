package models.worker;

import models.Model;

public abstract class Worker implements Model {

    private String name;
    private String city;
    private String country;

    Worker(String name, String city, String country) {
        this.name = name;
        this.city = city;
        this.country = country;
    }
}
