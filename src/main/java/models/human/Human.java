package models.human;

public abstract class Human {

    private int id;
    private String firstName;
    private String lastName;
    private String emailAddress;
    private String address;

    public Human(String firstName, String lastName) {
        this.id = -1;
        this.firstName = firstName;
        this.lastName = lastName;
        this.emailAddress = "not known";
        this.address = "not known";
    }

    public Human(int id, String firstName, String lastName) {
        this(firstName, lastName);
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setEmailAddress(String emailAddress) {
        this.emailAddress = emailAddress;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getFullName() {
        return String.format("%s %s", firstName, lastName);
    }

    public int getId() {
        return id;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public String getEmailAddress() {
        return emailAddress;
    }

    public String getAddress() {
        return address;
    }
}
