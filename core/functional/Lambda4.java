package functional;

import java.util.Comparator;

/***
 * Playing with comparator
 */
public class Lambda4 {
    public static void main(String[] args) {

        record Person(String firstName, String lastName, int age){}

        // Creating a comparator based on age.
        Comparator<Person> compareAge = (Person p1, Person p2) -> {return p1.age - p2.age;};
        Person p1 = new Person("Sam", "Nelson", 10);
        Person p2 = new Person("Pam", "Nelson", 35);
        System.out.println(compareAge.compare(p1, p2));

        Comparator<Person> complexComparator = Comparator.comparing(Person::firstName)
                .thenComparing(Person::lastName)
                .thenComparing(Person::age);
        System.out.println(complexComparator.compare(p1, p2));
    }
}
