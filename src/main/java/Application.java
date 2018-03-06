import factory.DbConnectionFactory;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class Application {

    public static void main(String[] args) {
        System.out.println("hi!");

        try {
            Connection connection = DbConnectionFactory.getConnection();
            if (connection != null) {
                String query = "SELECT * FROM Authors";
                PreparedStatement preparedStatement = connection.prepareStatement(query);
                ResultSet resultSet = preparedStatement.executeQuery();
                while (resultSet.next()) {
                    String name = resultSet.getString("name");
                    String surname = resultSet.getString("surname");
                    String fullName = String.format("%s %s", name, surname);
                    System.out.println(fullName);
                }
            }
        } catch(SQLException e) {
            e.printStackTrace();
        }
    }
}
