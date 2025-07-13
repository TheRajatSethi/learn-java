package A;

import org.springframework.stereotype.Component;

//@Component("thatSillyTennisCoach") // if you wish to define your own bean id you can provide a name
@Component  // Componenet annotation tells Spring to create a bean.
public class TennisCoach implements Coach{
    @Override
    public String getDailyWorkout() {
        return "I am a tennis coach you should practice for 1 hour";
    }
}
