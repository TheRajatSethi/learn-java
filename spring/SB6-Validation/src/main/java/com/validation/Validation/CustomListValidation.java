package com.validation.Validation;

import javax.validation.Constraint;
import java.lang.annotation.*;
import java.util.function.Predicate;

@Target({ ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = CustomListValidator.class)
@Documented
public @interface CustomListValidation {
    String message() default "Element not in list";

    Class<? extends Predicate<String>> runFunc();

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
