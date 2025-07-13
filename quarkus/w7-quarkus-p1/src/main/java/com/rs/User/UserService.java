package com.rs.User;


import com.rs.BcryptPasswordProvider;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import org.jboss.logging.Logger;

import java.util.Optional;
import java.util.UUID;

@ApplicationScoped
public class UserService {

    @Inject
    UserRepository userRepository;

    @Inject
    BcryptPasswordProvider bcryptPasswordProvider;

    private static final Logger LOG = Logger.getLogger(UserService.class);

    public boolean createUser(String email, String name, String password){
        Optional<UserEntity> e = userRepository.findByField("email", email);
        System.out.println(email);
        if (e.isPresent()){
            return false;
        }

        UserEntity userEntity = new UserEntity();
        userEntity.setName(name);
        userEntity.setEmail(email);
        userEntity.setPassword(bcryptPasswordProvider.encode(password));
        userEntity.setUuid(UUID.randomUUID().toString());
        userRepository.createUser(userEntity);
        return true;
    }

    public boolean loginUser(String email, String password){
        Optional<UserEntity> e = userRepository.findByField("email", email);

        if (e.isPresent()){
            return bcryptPasswordProvider.matches(password, e.get().getPassword());
        }else{
            return false;
        }
    }
}

