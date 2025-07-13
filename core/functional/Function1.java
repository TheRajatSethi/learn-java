package functional;


import java.util.function.Function;

public class Function1 {

    static class DoubleMeVerbose implements Function<Integer, Integer>{
        @Override
        public Integer apply(Integer integer) {
            return integer * 2;
        }
    }


    public static void main(String[] args) {
        DoubleMeVerbose d = new DoubleMeVerbose();
        System.out.println(d.apply(10));;

        Function<Integer, Integer> DoubleMeCompact = (x) -> x*2;
        System.out.println(DoubleMeCompact.apply(10));
    }

}
