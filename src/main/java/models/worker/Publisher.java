package models.worker;

public class Publisher extends Worker {

    private String publisherId;

    Publisher(String publisherId, String name, String city, String country) {
        super(name, city, country);
        this.publisherId = publisherId;
    }
}
