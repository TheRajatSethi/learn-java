package functional;

import java.util.function.Function;

public class Function2 {

    public static void main(String[] args) {
        class Triple implements Function<Integer, Integer>{

            @Override
            public Integer apply(Integer integer) {
                return integer*3;
            }
        }
        class Double implements Function<Integer, Integer>{
            @Override
            public Integer apply(Integer integer) {
                return integer*2;
            }
        }
        Function<Integer, Integer> HexTimes =  new Double().andThen(new Triple());
        System.out.println(HexTimes.apply(10));
    }
}
