package B;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class TennisCoach implements Coach {

    /**
     * Field based dependency injection
     */
//    @Autowired
    private FortuneService fortuneService;

    // Constructor based dependency injection.
    public TennisCoach(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyWorkout() {
        return "I am a tennis coach you should practice for 1 hour";
    }

    @Override
    public String getDailyFortune(){
        return this.fortuneService.getFortune();
    }
}
