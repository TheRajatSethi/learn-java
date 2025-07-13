package E;

import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

@Configuration
@ComponentScan("E")
@PropertySource("classpath:packageE.properties")
public class AppConfig {
}
