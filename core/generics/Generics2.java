package generics;

public class Generics2 {

    // A generic method
    public static <T> T methodA(T t) {
        return t;
    }

    // A generic method
    public static <T> void methodB(T t) {
    }

    public static <T extends Number> void methodC(T t) {
    }

    public static void main(String[] args) {
        String r1 = methodA("test");
        var r2 = methodA(20);
        methodB((Integer) 10);
    }

}
