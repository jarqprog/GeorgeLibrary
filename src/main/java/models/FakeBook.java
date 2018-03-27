package models;

public class FakeBook implements Model {

    private String title;

    public FakeBook(String title) {
        this.title = title;
    }

    @Override
    public String toString() {
        return "FakeBook{" +
                "title='" + title + '\'' +
                '}';
    }
}
