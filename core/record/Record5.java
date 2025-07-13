package record;


public class Record5 {
    public static void main(String[] args) {

        record Point(int x, int y){}

        enum Quadrant{
            TOP_LEFT,
            TOP_RIGHT,
            BOTTOM_LEFT,
            BOTTOM_RIGHT
        }

        Point p = new Point(10, 20);

        // Deconstruct records in pattern matching
        switch (p){
            case Point(int x, int y) when x<0 && y>0 -> System.out.println(Quadrant.TOP_LEFT);
            case Point(int x, int y) when x<0 && y<0 -> System.out.println(Quadrant.BOTTOM_LEFT);
            case Point(int x, int y) when x>0 && y>0 -> System.out.println(Quadrant.TOP_RIGHT);
            case Point(int x, int y) when x>0 && y<0 -> System.out.println(Quadrant.BOTTOM_RIGHT);
            default -> System.out.println("No specific quadrant");
        }

    }
}
