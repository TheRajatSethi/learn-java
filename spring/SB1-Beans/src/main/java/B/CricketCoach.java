package B;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class CricketCoach implements Coach{

    private FortuneService fortuneService;

    @Override
    public String getDailyWorkout() {
        return "hit 100 balls";
    }

    @Autowired  // setter injection
    public void setFortuneService(FortuneService fortuneService) {
        this.fortuneService = fortuneService;
    }

    @Override
    public String getDailyFortune() {
        return this.fortuneService.getFortune();
    }
}
