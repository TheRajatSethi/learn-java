package B;

import org.springframework.context.support.ClassPathXmlApplicationContext;

public class Main {
    public static void main(String[] args) {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("applicationContext.xml");
        Coach tcoach = context.getBean("tennisCoach", Coach.class);
        System.out.println(tcoach.getDailyWorkout());
        System.out.println(tcoach.getDailyFortune());

        Coach ccoach = context.getBean("cricketCoach", Coach.class);
        System.out.println(ccoach.getDailyWorkout());
        System.out.println(ccoach.getDailyFortune());
        context.close();
    }
}
