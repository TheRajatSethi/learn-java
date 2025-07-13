package C;

import org.springframework.stereotype.Component;

public class ClassA {

    private ClassB classB;

    public ClassA(ClassB classB) {
        this.classB = classB;
    }

    void m1(){
        System.out.println("In A:m1");
        this.classB.m1();
    }
}
