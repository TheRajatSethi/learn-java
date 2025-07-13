package functional;

import java.io.FileNotFoundException;
import java.util.stream.Collectors;

/***
 * joining
 */
public class Collector3 {
    public static void main(String[] args) throws FileNotFoundException {

    var books = Data.getData();

    var joinedTitles = books.stream()
            .map(Book::title)
            .filter(title -> title.contains("The Three"))
            .collect(Collectors.joining(" **** "));
        System.out.println("joinedTitles = "+ joinedTitles);

    }
}
