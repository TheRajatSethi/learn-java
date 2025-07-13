package E;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Main {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(AppConfig.class);
        Teacher sam = context.getBean(Teacher.class);
        System.out.println(sam.getName());
    }
}
