package functional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/***
 * Consumer
 * --------
 */
public class Lambda6 {
    public static void main(String[] args) {

        record Transaction(int id, double amount){}

        // Consumer can process a transaction record
        Consumer<Transaction> process = (t) -> {
            System.out.println((t.amount * 10));
            return;
        };

        Transaction t = new Transaction(10, 10.1);
        process.accept(t);

        // Stream on transactions and process them.
        List<Transaction> transactions = new ArrayList<>();
        transactions.add(t);
        // Process
        transactions.forEach(process);

        // Calling another consumer on each item once it's processed.
        transactions.forEach(process.andThen(process));

    }
}
