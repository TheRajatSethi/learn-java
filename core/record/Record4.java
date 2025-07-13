package record;

import java.io.Serializable;

public class Record4 {

    // None of the fields can be transient
    record Employee(int age, String name) implements Serializable {}

    // normal class can have transient fields which are omitted during serialization
    static class Employer implements Serializable{
        transient int companyIncorporationYear;
        String name;
    }

    record LowerEmployee() {}

    public static void main(String[] args) {
        Employee sam = new Employee(25, "Sam");
    }

}
