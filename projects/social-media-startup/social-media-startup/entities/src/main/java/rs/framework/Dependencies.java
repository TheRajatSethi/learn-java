package rs.framework;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

public class Dependencies {

    /**
     * Initialize Dependencies.
     */
    public static void initialize(){
        dbConnection = "sadfas";
    }

    /**
     * Jackson Mapper with JSR310 JavaTimeModule
     */
    private static final ObjectMapper mapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    public static ObjectMapper getObjectMapper() {
        return mapper;
    }

    /**
     * Database Connection
     */
    private static  String dbConnection;
    public static String getDbConnection() {
        return dbConnection;
    }
}
