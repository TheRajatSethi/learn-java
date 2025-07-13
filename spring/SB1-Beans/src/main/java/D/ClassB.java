package D;

import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("prototype")
public class ClassB {
    void m1(){
        System.out.println("ClassB:m1");
    }
}
