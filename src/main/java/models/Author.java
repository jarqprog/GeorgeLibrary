package models;

public class Author extends Worker {

    private int authorId;
    private String surname;
    private int birthYear;

    public Author(int authorId, String name, String surname, int birthYear, String city, String country) {
        super(name, city, country);
        this.authorId = authorId;
        this.surname = surname;
        this.birthYear = birthYear;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getSurname() {
        return surname;
    }

    public int getBirthYear() {
        return birthYear;
    }
}
