package sk.spedry.weebcollector.properties;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Configuration {

    private final Logger logger = LogManager.getLogger(this.getClass());

    private Properties properties;

    public Configuration() {
        this.properties = new Properties();
        try (InputStream inputStream = Configuration.class.getClassLoader().getResourceAsStream("sk/spedry/weebcollector/config.properties")) {
            this.properties.load(inputStream);
        } catch (IOException e) {
            logger.error("Error exception when loading input stream: {}", e.toString());
        }
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}
