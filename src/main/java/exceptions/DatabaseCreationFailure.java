package exceptions;

public class DatabaseCreationFailure extends Exception {

    private final String message;

    public DatabaseCreationFailure() {
        message = "Problem occurred in database creation procedure";
    }

    public String getMessage() {
        return message;
    }
}
