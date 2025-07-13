package record;

import java.lang.annotation.*;

public class Record2 {

    // Creating annotation
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.FIELD)
    @interface GreaterThanZero { }

    // Omitting annotation processor

    // Record is using the annotation on a field
    record employee(@GreaterThanZero int age, String name){}

}
