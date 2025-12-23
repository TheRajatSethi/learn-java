# JDBI

```java
package com.rs.stocks.users;

import com.rs.stocks.models.User;
import org.jdbi.v3.core.Jdbi;
import org.jdbi.v3.core.mapper.reflect.ConstructorMapper;

public class Main {
    public static void main(String[] args) {
        D.initializeDependencies();
        Jdbi jdbi = D.getJdbi();

        var result = jdbi.withHandle(handle -> {
            return handle.createQuery("Select * from \"user\"")
                    .registerRowMapper(ConstructorMapper.factory(User.class))
                    .mapTo(User.class).one();
        });

        var result2 = jdbi.withExtension(UserDao.class, dao ->
                dao.getUserById(1)
        );

    }
}
```

JDBI with records - Working.

```java
package com.rs.stocks.users;

import com.rs.stocks.models.User;
import org.jdbi.v3.sqlobject.config.RegisterConstructorMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindMethods;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.Optional;

interface UserDao {

    @SqlQuery("Select * from \"user\" where id = :param")
    @RegisterConstructorMapper(User.class)
    Optional<User> getUserById(@Bind("param") int id);

    @SqlQuery("Select * from \"user\" where email = :param")
    @RegisterConstructorMapper(User.class)
    Optional<User> getUserByEmail(@Bind("param") String email);

    @SqlQuery("Insert into \"user\" (email, password, username) values (:email, :password, :username) returning id")
    Integer insertUser(@BindMethods User user);

}

```

JDBI with bean working

```java

@Getter
@Setter
@ToString
@NoArgsConstructor
public class User {
    Integer id;
    String email;
    String password;
    String username;
    ZonedDateTime createdAt;
}
```

## JDBI Sharp corners

`@Accessor(chain=true) does not work` - This issue is tracked in - https://github.com/jdbi/jdbi/issues/1199

