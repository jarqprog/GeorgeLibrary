package com.jarq.system.models.human.user;

import com.jarq.system.models.human.Person;
import com.jarq.system.models.human.author.IAuthor;

import java.util.List;

public class User extends Person implements IAuthor, IUser {

    private String password;
    private int repositoryId;

    public User(String firstName, String lastName, String email) {
        super(firstName, lastName);
        setEmail(email);
        this.password = "123";
    }

    public User(int id, String firstName, String lastName,
                String email, String password, int repositoryId) {
        super(id, firstName, lastName);
        setEmail(email);
        this.password = password;
        this.repositoryId = repositoryId;
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

    @Override
    public int getRepositoryId() {
        return repositoryId;
    }
}
