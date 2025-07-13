package completableFuture;

import java.util.concurrent.CompletableFuture;

/**
 * <b>Asynchronous Programming in Java.</b>
 * <hr>
 * Future - Pre Java-8
 * <br>
 * CompletableFuture - Evolved version of Future
 * <br>
 * CompletableFuture = JavaScript's promise
 * <hr>
 * Let's say there is some function {@link #compute(int)} which takes a long time to run.
 * Or we wish to execute that in async fashion. You can wrap that method in a
 * <code>CompletableFuture</code> which can supply the result in async way when its
 * available. E.g. {@link #computeAsync(int)}
 * You will also see that the threads which are printed are not always the same
 * because JVM at runtime decides whether to create a async is executed in a ForkJoinPool.commonPool-worker
 * or just continue the execution in main thread.
 * <br><br>
 * In this example we never wait for <code>CompletableFuture</code> thus if there is a delay you will
 * not see the output itself.
 */
public class CF1 {

    // This is a simple function which does some computation.
    static Integer compute(int n) {
        // If you add the below delay you will see that result is not even printed.
        // Because we are not waiting for CompletableFuture to return and the main thread
        // continues to execute to the end and completes the program.
        try{Thread.sleep(100);}catch (Exception ignored){};
        System.out.println("In compute function - " + Thread.currentThread());
        return n * 2;
    }

    static CompletableFuture<Integer> computeAsync(int n) {
        return CompletableFuture.supplyAsync(() -> compute(n));
    }

    public static void main(String[] args) {

        // Creating Completable Future
        CompletableFuture.supplyAsync(() -> compute(10)); // non-blocking because it's a CompletableFuture

        // Using a function which returns a Completable Future
        computeAsync(10) // non-blocking because returns CompletableFuture
                .thenApply((n) -> { // Chaining some operations on completable future
                    System.out.println("In Main - At compute async.thenApply function - " + Thread.currentThread());
                    return n + 1;
                })
                .thenAccept(System.out::println);
        System.out.println("Computation Started ...");
    }
}
