package C;

import org.springframework.context.annotation.*;

@Configuration
public class AppConfig {

    @Bean
    @Scope("prototype")
    ClassB classB(){
        System.out.println("Creating bean for ClassB");
        return new ClassB();
    }

    @Bean
    ClassA classA(){
        System.out.println("Creating bean for ClassA");
        return new ClassA(classB());
    }

    @Bean
    @Scope("prototype")
    ClassC classC(){
        System.out.println("Creating bean for ClassC");
        return new ClassC(classB());
    }
}
