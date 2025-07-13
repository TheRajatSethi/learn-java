package com.validation.Validation;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.function.Predicate;

public class CustomListValidator implements ConstraintValidator<CustomListValidation, String> {
    Class<? extends Predicate<String>> mClass;
    String message;


    @Override
    public void initialize(CustomListValidation constraintAnnotation) {
        ConstraintValidator.super.initialize(constraintAnnotation);
        this.mClass = constraintAnnotation.runFunc();
        this.message = constraintAnnotation.message();
    }

    @Override
    public boolean isValid(String s, ConstraintValidatorContext constraintValidatorContext) {
        System.out.println("In CustomListValidator is Valid");
        try {
            System.out.println(this.mClass.getMethod("test", Object.class));
            this.mClass.getMethod("test", Object.class).invoke(mClass.newInstance());
//            return (boolean) this.runFunc.getDeclaredMethod("test").invoke(s);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
