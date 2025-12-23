package com.rs.stocks.users;

import com.rs.stocks.models.UserModel;
import org.jdbi.v3.sqlobject.config.RegisterBeanMapper;
import org.jdbi.v3.sqlobject.customizer.Bind;
import org.jdbi.v3.sqlobject.customizer.BindBean;
import org.jdbi.v3.sqlobject.statement.SqlQuery;

import java.util.Optional;

interface UserDao {

    @SqlQuery("Select * from \"user\" where id = :param")
    @RegisterBeanMapper(UserModel.class)
    Optional<UserModel> getUserById(@Bind("param") int id);

    @SqlQuery("Select * from \"user\" where email = :param")
    @RegisterBeanMapper(UserModel.class)
    Optional<UserModel> getUserByEmail(@Bind("param") String email);

    @SqlQuery("Insert into \"user\" (email, password, username) values (:email, :password, :username) returning id")
    Integer insertUser(@BindBean UserModel user);

}
