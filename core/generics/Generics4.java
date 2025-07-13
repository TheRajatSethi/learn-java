package generics;

import java.util.List;
import java.lang.Number;

public class Generics4 {

    public static <T extends Number> void f1(List<T> l, T t){

    }

    public static void f2(List<? super Number> l, Integer t){

    }

    public static void main(String[] args) {

    }

}
