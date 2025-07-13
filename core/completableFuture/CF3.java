package completableFuture;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class CF3 {

    static Integer compute(int n) {
        System.out.println("Staring compute process in thread "+ Thread.currentThread());
        try{Thread.sleep(100);}catch (Exception ignored){};
        System.out.println("Finishing compute process");
        return n * 2;
    }

    public static void main(String[] args) {

        ExecutorService executorService = Executors.newSingleThreadExecutor();

        // Running on our defined ThreadPool
        var cf = CompletableFuture.supplyAsync(() -> compute(10), executorService);

        // Doing some other work until the CF is executing.
        System.out.println("Starting some other work in parallel in thread" + Thread.currentThread());
        try{Thread.sleep(10);}catch (Exception ignored){};
        System.out.println("Finished some other work in parallel");

        cf.join(); // Waiting CF to be done here. // FIXME - Some bug which does not let the program to terminate
        System.out.println("Finished Main");
        System.exit(0); // FIXME - Temp solution for the above.
    }
}
