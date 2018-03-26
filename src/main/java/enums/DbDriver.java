package enums;

public enum DbDriver {

    SQLITE("org.sqlite.JDBC");

    private String driver;

    DbDriver(String driver) {
        this.driver = driver;
    }

    public String getDriver() {
        return driver;
    }

}
