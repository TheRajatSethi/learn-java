package theads;

public class Thread3 {
    public static void main(String[] args) throws InterruptedException {

        Runnable runnable = () ->{
            System.out.println("Running in thread");
            try {
                Thread.sleep(600);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        };

        Thread t = new Thread(runnable);
        t.start();
        Thread.sleep(200);
    }
}
