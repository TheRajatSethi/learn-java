package com.rs.User;

import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;
import org.jdbi.v3.core.Jdbi;

import java.util.Optional;

@ApplicationScoped
public class UserRepository {

    Logger LOG = Logger.getLogger(UserRepository.class);

    @Inject
    Jdbi jdbi;

    public Optional<UserEntity> findByField(String field, String value){
        return jdbi.withHandle(handle -> handle.select("Select * from Users where %s = ?".formatted(field), value)
                .mapToBean(UserEntity.class).findFirst());
    }

    public void createUser(UserEntity userEntity){
        jdbi.withHandle(handle -> handle.createUpdate("Insert into Users (name, uuid, email, password) values (:name, :uuid, :email, :password)")
                .bindBean(userEntity)
                .execute());
    }
}