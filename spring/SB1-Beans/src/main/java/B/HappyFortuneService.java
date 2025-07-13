package B;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Primary
@Scope("singleton")
public class HappyFortuneService implements FortuneService {
    @Override
    public String getFortune() {
        return "Have a great day.";
    }

    @PostConstruct
    void init(){
        System.out.println("Running Bean HappyFortune Service - init....");
    }


    @PreDestroy
    void destroy(){
        System.out.println("Running Bean HappyFortune Service - destroy....");
    }
}
