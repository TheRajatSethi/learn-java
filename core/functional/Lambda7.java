package functional;

import java.time.LocalDate;
import java.util.function.Consumer;

/***
 * Currency converter
 * ------------------
 * Very Interesting
 * In functional programming this is called "partial application".
 * In this talk they design a very simple API which converts currency from
 * one to another for a particular date.
 * .
 * Partial application is little different from currying, where partial
 * application the "things" which the various functions do is very
 * different from each other.
 * .
 * Youtube link below
 * https://www.youtube.com/watch?v=ePXnCezwRuw
 */
public class Lambda7 {
    public static void main(String[] args) {

        interface CurrencyConverter{
            static Consumer<String> of(LocalDate date){
                return (String s) -> s.length();
            }
        }



        // FIXME : Unable to figure this out.
//        CurrencyConverter
//                .of(LocalDate.now())
//                .from("CAD")

    }
}
