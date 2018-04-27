package models.human.user;

import models.human.Human;
import models.human.author.IAuthor;

import java.util.List;

public class User extends Human implements IAuthor, IUser {

    // todo: proper constructors
    public User(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public User(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    @Override
    public List<String> getFullData() {
        return null;
    }
}
