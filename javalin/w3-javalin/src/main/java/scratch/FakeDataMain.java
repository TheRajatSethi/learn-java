package scratch;

import com.github.javafaker.Faker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class FakeDataMain {
    static Faker faker = new Faker();
    public static void main(String[] args) throws SQLException {

        String url = "jdbc:sqlite:database.db";
        Connection connection = DriverManager.getConnection(url);
        PreparedStatement ps = connection.prepareStatement("INSERT INTO USERS (name, email, password) values(?, ?, ?)");

        for (int i = 0; i < 10000; i++) {
            var data = generateRandomData();
            ps.setString(1, data.name);
            ps.setString(2, data.email);
            ps.setString(3, data.password);
            ps.execute();
        }

    }

    record Data(String name, String email, String password){}

    static Data generateRandomData(){
        String name = faker.name().fullName();
        String email = name.replace(" ","") + "@gmail.com";
        String password = faker.random().hex();
        return new Data(name, email, password);
    }
}
