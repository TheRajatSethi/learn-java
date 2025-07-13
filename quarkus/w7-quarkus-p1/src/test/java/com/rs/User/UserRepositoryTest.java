package com.rs.User;

import io.quarkus.test.junit.QuarkusTest;
import jakarta.inject.Inject;
import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@QuarkusTest
class UserRepositoryTest {

    @Inject
    UserRepository userRepository;

    @Test
    public void a(){
        System.out.println(userRepository.findByField("email", "sam.nelson@gmail.com"));
        System.out.println(userRepository.findByField("id", "2"));
    }

    @Test
    public void createUserTest(){
        UserEntity userEntity = new UserEntity();
        userEntity.setName("Lita Nelson");
//        userEntity.setUuid("Lita Nelson");
//        userEntity.setPassword("Lita Nelson");
//        userEntity.setEmail("Lita Nelson");
        userRepository.createUser(userEntity);
    }

    @Test
    public void b(){
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        var encodedPassword = bCryptPasswordEncoder.encode("password");
        System.out.println(bCryptPasswordEncoder.matches("Password", encodedPassword));
    }
}