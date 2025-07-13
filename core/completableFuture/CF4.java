package completableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * This is a callback example
 */
public class CF4 {

    static void delay(int i){
        try{Thread.sleep(i * 1000L);}catch (Exception ignored){};
    }

    static Integer compute(int n) {
        delay(1);
        return n * 2;
    }

    public static void main(String[] args) {
        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> compute(10));
        // Do some other work ...
        cf.join();
        cf.thenAccept(System.out::println); // also can use thenApply(Function), thenRun(Runnable).
    }
}
