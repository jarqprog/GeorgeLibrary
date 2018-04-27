package models.human.author;

import models.human.Human;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Author extends Human implements IAuthor {

    public Author(String firstName, String lastName) {
        super(firstName, lastName);
    }

    public Author(int id, String firstName, String lastName) {
        super(id, firstName, lastName);
    }

    @Override
    public List<String> getFullData() {
        return new ArrayList<>(Arrays
                .asList(String.valueOf(getId()), getFirstName(), getLastName(), getEmailAddress(), getAddress()));
    }
}
