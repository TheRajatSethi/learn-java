package completableFuture;

/**
 * This is a callback example
 */
public class CF5 {

    static void delay(int i){
        try{Thread.sleep(i * 1000L);}catch (Exception ignored){};
    }

    static int computeMore(int n) {
        delay(2);
        return n * 2;
    }
    static int compute(int n) {
        delay(1);
        return n * 2;
    }

    public static void main(String[] args) {
//        CompletableFuture<Integer> cf = CompletableFuture.supplyAsync(() -> compute(10));
//        cf.thenCompose(d -> computeMore(d)); // also can use thenApply(Function), thenRun(Runnable).
    }
}
