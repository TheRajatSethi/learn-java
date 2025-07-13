package functional;

import java.io.FileNotFoundException;
import java.util.Comparator;
import java.util.stream.Collectors;

/***
 * Counting, maxBy
 * ----------------
 */
public class Collector1 {
    public static void main(String[] args) throws FileNotFoundException {

        var data = Data.getData();

        /***
         * Counting
         */
        // Counting Total Number of books in the collection.
        System.out.println(data.stream().count());
        // Same using collectors
        System.out.println(data.stream().collect(Collectors.counting()));


        /***
         * maxBy
         */
        // Map book to number of pages.
        // Get max which takes in a comparator
        // Just want natural order comparator
        // Which returns an optional
        // use get() to get value from the optional.
        System.out.println(data.stream()
                .map(Book::noPages)
                .max(Comparator.naturalOrder())
                .get());
        // Same using collectors
        System.out.println(data.stream()
                .map(Book::noPages)
                .collect(Collectors.maxBy(Comparator.naturalOrder()))
                .get());



    }
}
