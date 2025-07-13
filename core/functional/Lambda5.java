package functional;

import java.time.LocalDate;

/***
 * The standard comparator is over a single generic object.
 * In this example lets create a comparator which can compare
 * two different types of objects.
 */
public class Lambda5 {
    public static void main(String[] args) {

        // A simpler implementation of a comparator which can compare
        // two different types of objects.
        @FunctionalInterface
        interface Comparator<T, U>{
            int compare(T t, U u);
            boolean equals(Object obj); // As this is overriding this does not count
            // towards 1 abstract method in a functional interface
        }

        record Person(String name, int age){}
        record Car(String make, int model){}

        Person p = new Person("Sam", 10);
        Car c = new Car("Toyota", 2019);

        Comparator<Person, Car> compareAgeCar = (Person, Car) -> Person.age - (LocalDate.now().getYear() - Car.model);
        System.out.println(compareAgeCar.compare(p, c));

    }
}
