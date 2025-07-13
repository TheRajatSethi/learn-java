package record;

public class Record3 {
    record Point(int x, int y) implements Runnable {
        @Override
        public void run() {
            System.out.println(this);
        }
    }

    public static void main(String[] args) {
        Thread thread = new Thread(new Point(10, 20));
        thread.start();
    }
}
