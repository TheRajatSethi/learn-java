package scratch;

public class BuilderPattern {
    public static void main(String[] args) {
        var some = SomeBuilder.newBuilder().withConfigA(10).withConfigB(20).build();
        System.out.println(some);
    }
}

class Some{
    int valueA;
    int valueB;
    public Some(int a, int b){
        this.valueA = a;
        this.valueB = b;
    }

    @Override
    public String toString() {
        return "Some{" +
                "valueA=" + valueA +
                ", valueB=" + valueB +
                '}';
    }
}

class SomeBuilder{

    private SomeBuilder(){}

    int configA;
    int configB;

    public static SomeBuilder newBuilder(){
        return new SomeBuilder();
    }

    public SomeBuilder withConfigA(int a){
        this.configA = a;
        return this;
    }
    public SomeBuilder withConfigB(int b){
        this.configB = b;
        return this;
    }

    public Some build(){
       return new Some(this.configA, this.configB);
    }

}