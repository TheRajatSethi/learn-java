# JDBI

Having lots of issues with JDBI, thus for now using raw jdbc.

- Unable to map
- Connection closed
- ...

```java
Jdbi jdbi = Jdbi.create("jdbc:sqlite:database.db").installPlugin(new SQLitePlugin());

var users = jdbi.withHandle(
        handle -> handle.createQuery("SELECT * FROM USERS").mapToMap()
);

System.out.println(users.first());
```


Insert Fake data into Users Table

```java
import com.github.javafaker.Faker;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class scratch.FakeDataMain {
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
```


---

**General Java Issue** : One of the problem with Java is that you have to write a lot of code for a simple CRUD api. If you see this project there seems to be 5 files just to have a simple CRUD api.


---

# JDBI

Finally was able to get JDBI working.

## POJO

User POJO
```java
public class User {
    private int id;
    private String name;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public User() {
    }

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", name='" + name + '\'' +
                '}';
    }
}
```

```java
// Works with POJO
handle.createQuery("SELECT * FROM user ORDER BY name")
    .registerRowMapper(ConstructorMapper.factory(User.class))
    .mapToBean(User.class)
    .list();
```


## Record

[Record](https://github.com/jdbi/jdbi/issues/1822)

```java
public record User(int id, String name) {}


handle.createQuery("SELECT * FROM user ORDER BY name")
    .registerRowMapper(ConstructorMapper.factory(User.class))
    .mapTo(User.class)
    .list();
```
