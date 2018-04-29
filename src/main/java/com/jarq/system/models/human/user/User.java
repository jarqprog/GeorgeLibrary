package com.jarq.models.human.user;

import com.jarq.models.human.Human;
import com.jarq.models.human.author.IAuthor;

import java.util.List;

public class User extends Human implements IAuthor, IUser {

    private String password;

    public User(String firstName, String lastName, String email) {
        super(firstName, lastName);
        setEmail(email);
        this.password = "123";
    }

    public User(int id, String firstName, String lastName, String email, String password) {
        super(id, firstName, lastName);
        setEmail(email);
        this.password = password;
    }

    @Override
    public List<String> getFullData() {
        return null;
    }

    @Override
    public void setPassword(String password) {
        this.password = password;
    }

    @Override
    public String getPassword() {
        return password;
    }
}
