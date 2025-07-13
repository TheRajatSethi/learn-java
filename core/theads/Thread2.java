package theads;

public class Thread2 {

    public static class MyThread implements Runnable{
        @Override
        public void run() {
            System.out.println("Running in a thread!!");
        }
    }

    public static void main(String[] args) {
        Thread t = new Thread(new MyThread());
        t.start();
    }

}
