package dataStructure;

import java.util.EnumMap;
import java.util.Map;
import java.util.function.Function;

/**
 * EnumMap
 */
public class DS2 {
    private enum Choice {
        SQUARE,
        CUBE
    }

    public static void main(String[] args) {

        Map<Choice, Function<Integer, Integer>> operation = new EnumMap<>(Choice.class);

        Function<Integer, Integer> square = n -> n * n;
        Function<Integer, Integer> cube = n -> n * n * n;

        operation.put(Choice.SQUARE, square);
        operation.put(Choice.CUBE, cube);

        System.out.println(compute(10, operation, Choice.CUBE));
        System.out.println(compute(20, operation, Choice.SQUARE));
    }

    private static int compute(int num, Map<Choice, Function<Integer, Integer>> operation, Choice c){
        return operation.get(c).apply(num);
    }
}
