import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileReader;
import java.io.IOException;
import java.util.Properties;

public class ConfigFactory {

    int port;
    Logger logger = LoggerFactory.getLogger(ConfigFactory.class);

    private ConfigFactory(){
        // Set global log level

        logger.info("Reading Config");

        readConfig();

    }

    private void readConfig() {
        try{
            FileReader fr = new FileReader("src/main/resources/application.properties");
            Properties config = new Properties();
            config.load(fr);

            this.port = Integer.parseInt(config.getProperty("Server.port"));
            logger.info("Server port is {}", this.port);

        } catch (IOException e) {
            logger.error("Config loading failed - {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private static ConfigFactory instance;

    public static ConfigFactory getConfig(){
        if (instance == null){
            instance = new ConfigFactory();
        }
        return instance;
    }

}
