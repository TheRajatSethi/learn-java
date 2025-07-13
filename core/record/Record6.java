package record;

import java.io.*;


public class Record6 {
    public static void main(String[] args) throws IOException {


        record Point(int x, int y) implements Serializable{}

        Point p = new Point(10, 20);

        var fileOutputStream = new FileOutputStream("record.txt");
        var objectOutputStream = new ObjectOutputStream(fileOutputStream);

        objectOutputStream.writeObject(p);
        objectOutputStream.flush();
        objectOutputStream.close();

    }
}
