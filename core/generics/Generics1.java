package generics;

import java.util.List;

class Sample1<T> {
    int x;
    T t;

    public Sample1(int x, T t) {
        this.x = x;
        this.t = t;
    }

    T getT() {
        return t;
    }
}

public class Generics1 {

    public static void main(String[] args) {
        Sample1<String> s1 = new Sample1<>(10, "test");
        System.out.println(s1.getT());
        Sample1<List<String>> s2 = new Sample1<>(10, List.of("test"));
        System.out.println(s2.getT());


        // The raw use of parameter is discouraged as it leads to code which is not typesafe.
        Sample1<List> s3 = new Sample1<>(10, List.of("test", 10, List.of(10, 20, "test")));
    }

}
