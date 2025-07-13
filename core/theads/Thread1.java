package theads;

public class Thread1 {

    static class X extends Thread{
        public void run(){
            System.out.println("Running in thread");
        }
    }

    public static void main(String[] args) {
        X x = new X();
        x.start();
    }

}
