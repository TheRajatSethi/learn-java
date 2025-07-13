package functional;

import java.util.function.Function;
import java.util.function.Predicate;

/***
 * Playing with Predicate
 * ----------------------
 */
public class Lambda3 {

    public static final String NAME = "Sam Nelson";

    public static void main(String[] args) {

        Predicate<String> lengthy = (s) -> s.length() > 100;
        System.out.println(lengthy.test(NAME));

        Predicate<String> contains = (s) -> s.contains(NAME);
        System.out.println(contains.test(NAME));

        // Now we can start to combine two predicates for a combination of checks.
        Predicate<String> combinator = (s) -> {
            return contains.test(s) && lengthy.test(s);
        };
        System.out.println(combinator.test(NAME));

        // Another way to combine
        Predicate<String> combinatorV2 = lengthy.and(contains);
        Predicate<String> combinatorV3 = lengthy.or(contains);
        // Use the above predicates
        System.out.println(combinatorV2.test(NAME));
        System.out.println(combinatorV3.test(NAME));

        // Negation
        var combinatorV4 = lengthy.negate().and(contains).or(lengthy);


    }
}
