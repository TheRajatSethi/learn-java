package functional;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Data {

    static List<Book> getData() throws FileNotFoundException {
        List<Book> books = new ArrayList<>();
        try(var br = new BufferedReader(new FileReader("src/main/resources/books.csv"))){
            String line;
            br.readLine(); // skip header
            while((line = br.readLine()) != null){
                String[] values = line.split(",");
                books.add(
                        new Book(
                                Integer.parseInt(values[0]),
                                values[1],
                                values[2],
                                Double.parseDouble(values[3]),
                                values[4],
                                Long.parseLong(values[5]),
                                values[6],
                                Integer.parseInt(values[7]),
                                Integer.parseInt(values[8]),
                                Integer.parseInt(values[9]),
//                                LocalDate.parse(values[10]),
                                values[11]
                        )
                );
            }
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return books;
    }

    public static void main(String[] args) throws IOException {

    }
}
