package annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

public class A1 {

    /**
     * Creating a custom annotation
     */
    @Target(ElementType.TYPE)
    @Retention(RetentionPolicy.RUNTIME)
    @interface MyAnnotation{ }

    /**
     * Annotating a class with the custom annotation
     */
    @MyAnnotation
    static
    class Class1{}

    public static void main(String[] args) {
        System.out.println(Class1.class.isAnnotationPresent(MyAnnotation.class));
    }
}
