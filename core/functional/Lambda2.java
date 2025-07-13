package functional;

import java.util.Arrays;

/***
 * Functional Interface
 * --------------------
 * A functional interface has exactly one abstract method.
 * Since default methods have an implementation, they are not abstract.
 * Functional interfaces can then be used in
 * - lambda expressions
 * - method references
 * - constructor references.
 */

public class Lambda2 {
    public static void main(String[] args) {

        // Defined a functional interface
        @FunctionalInterface
        interface X{
            void x();

            default void y(){
                System.out.println("In y");
            }

            static void z(){
                System.out.println("In y");
            }
        }

        // Creating an implementation of FI using class.
        class Foo implements X{
            @Override
            public void x() {
                System.out.println("In X");
            }
        }
        Foo foo = new Foo();
        foo.x();

        // Creating an implementation of FI using lambda.
        X x1 = () -> System.out.println("In X");
        x1.x();

    }
}
