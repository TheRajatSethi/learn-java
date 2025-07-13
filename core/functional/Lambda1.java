package functional;

import java.util.ArrayList;
import java.util.List;

/***
 * Internalizing the loop.
 * ----------------------
 * Before forEach the developer told Java that take each element and do something with it in order.
 * With the introduction of forEach we tell Java that do something with each element. Order is dictated
 * by the internal library author, who may choose to parallelize the request
 */
public class Lambda1 {
    public static void main(String[] args) {

        List<Integer> numbers = new ArrayList<>(List.of(1,2,4,5,6));

        // External loop - dev provides iteration logic.
        for (var num: numbers){
            System.out.println(num);
        }

        // Internal loop - library author provides iteration logic.
        numbers.forEach(System.out::println);

    }
}
