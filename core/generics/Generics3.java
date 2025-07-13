package generics;

import java.util.List;

public class Generics3 {
    // Single bound condition
    public static <T extends Number> void methodA(T t){

    }
    // Multiple bound condition
    // Raw use of List is not recommended - just for demo purposes.
    public static <T extends Number & List> void methodB(){

    }
}
