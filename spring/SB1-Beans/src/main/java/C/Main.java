package C;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {

        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);

        ClassA ca1 = context.getBean(ClassA.class);
        ca1.m1();

        ClassA ca2 = context.getBean(ClassA.class);
        ca2.m1();
        System.out.println();

        ClassC cc1 = context.getBean(ClassC.class);
        cc1.m1();

        ClassC cc2 = context.getBean(ClassC.class);
        cc2.m1();


    }
}
