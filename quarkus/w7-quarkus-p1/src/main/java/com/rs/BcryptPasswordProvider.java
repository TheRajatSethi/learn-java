package com.rs;

import jakarta.annotation.PostConstruct;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.ext.Provider;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

@ApplicationScoped
public class BcryptPasswordProvider extends BCryptPasswordEncoder {

    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @PostConstruct
    void init(){
        bCryptPasswordEncoder = new BCryptPasswordEncoder();
    }

    @Produces
    public BCryptPasswordEncoder provideBCryptPasswordEncoder(){
        return bCryptPasswordEncoder;
    }
}
