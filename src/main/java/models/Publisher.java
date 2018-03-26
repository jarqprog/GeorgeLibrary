package models;

public class Publisher extends Worker {

    private String publisherId;

    public Publisher(String publisherId, String name, String city, String country) {
        super(name, city, country);
        this.publisherId = publisherId;
    }

    public String getPublisherId() {
        return publisherId;
    }
}
