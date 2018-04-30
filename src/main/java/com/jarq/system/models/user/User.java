package com.jarq.system.models.user;

import com.jarq.system.models.Model;
import com.jarq.system.models.address.IAddress;
import com.jarq.system.models.repository.IRepository;

import java.util.ArrayList;
import java.util.List;

public class User extends Model implements IUser {

    private String name;
    private String surname;
    private String email;
    private String password;
    private IAddress address;
    private List<IRepository> repositories = new ArrayList<>();


    User(int id, String name, String surname, String email, String password, IAddress address) {
        setId(id);
        this.name = name;
        this.surname = surname;
        this.email = email;
        this.password = password;
        this.address = address;
    }

    @Override
    public void setName(String name) {
        this.name = name;
    }

    @Override
    public void setSurname(String surname) {
        this.surname = surname;
    }

    @Override
    public void setEmail(String email) {
        this.email = email;
    }

    @Override
    public void setAddress(IAddress address) {
        this.address = address;
    }

    @Override
    public String getFullName() {
        return String.format("%s %s", name, surname);
    }

    @Override
    public String getName() {
        return name;
    }

    @Override
    public String getSurname() {
        return surname;
    }

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public IAddress getAddress() {
        return address;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public List<IRepository> getRepositories() {
        return repositories;
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + getId() +
                ", name='" + name + '\'' +
                ", surname='" + surname + '\'' +
                ", email='" + email + '\'' +
                ", address=" + address +
                ", repositories=" + repositories +
                '}';
    }
}
