package E;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Component
public class Teacher {
    @Value("${name}")
    private String name;

    public String getName() {
        return name;
    }
}
