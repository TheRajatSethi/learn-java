package C;

public class ClassC {

    private ClassB classB;

    public ClassC(ClassB classB) {
        this.classB = classB;
    }

    void m1(){
        System.out.println("In C:m1");
        this.classB.m1();
    }
}
