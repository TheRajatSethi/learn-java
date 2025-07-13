package com.validation.Validation;

import java.util.function.Predicate;

public class MyValidator implements Predicate<String> {
    @Override
    public boolean test(String s) {
        System.out.println("In Validator Predicate");
        return true;
    }
}
