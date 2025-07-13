package record;

public class Record1 {

    record point(Integer x, Integer y){
        public point{ // Compact Constructor - Note the omission of `()`
            if (x < 10){
                throw new IllegalArgumentException("Value cannot be less than 10");
            }
        }
    }

    public static void main(String[] args) {
        var p1 = new point(10, 20); // Throws IllegalArgumentException
        var p2 = new point(10, null); // If Integer can be passed in null, if int then no.
        var p3 = new point(1, 2); // Throws IllegalArgumentException
    }
}