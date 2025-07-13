package functional;

import java.io.FileNotFoundException;
import java.util.stream.Collectors;

/***
 * summarizingInt
 */
public class Collector2 {
    public static void main(String[] args) throws FileNotFoundException {

        var books = Data.getData();

        // Statistical summary of Book::noPages using stream api
        System.out.println(books.stream()
                // mapToInt converts the books to integer stream
                .mapToInt(Book::noPages)
                .summaryStatistics());

        // Statistical summary of Book::noPages using collectors api
        System.out.println(books.stream()
                .collect(Collectors.summarizingInt(Book::noPages)));

    }
}
