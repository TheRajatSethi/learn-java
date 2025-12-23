package rs.customer.service.coreCustomer.configuration;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.PropertyNamingStrategies;
import com.fasterxml.jackson.databind.json.JsonMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
class Config {
    @Bean
    public ObjectMapper getObjectMapper(){
        return new JsonMapper()
                .setPropertyNamingStrategy(PropertyNamingStrategies.SnakeCaseStrategy.INSTANCE)
                .findAndRegisterModules();
    }
}
