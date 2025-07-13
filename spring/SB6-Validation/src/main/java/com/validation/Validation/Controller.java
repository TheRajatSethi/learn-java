package com.validation.Validation;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
public class Controller {

    @PostMapping("/")
    public void validate(@Valid @RequestBody Payload payload){
        System.out.println(payload);
    }

}
