package completableFuture;

import java.util.concurrent.CompletableFuture;

public class CF2 {

    static Integer compute(int n) {
        System.out.println("Staring compute process in thread "+ Thread.currentThread());
        try{Thread.sleep(100);}catch (Exception ignored){};
        System.out.println("Finishing compute process");
        return (Integer) (n * 2);
    }

    public static void main(String[] args) {

        var cf = CompletableFuture.supplyAsync(() -> compute(10)); // non-blocking because it's a CompletableFuture

        // Doing some other work until the CF is executing.
        System.out.println("Starting some other work in parallel in thread" + Thread.currentThread());
        try{Thread.sleep(10);}catch (Exception ignored){};
        System.out.println("Finished some other work in parallel");

        cf.join(); // Waiting CF to be done here.
        System.out.println("Finished Main");
    }
}
