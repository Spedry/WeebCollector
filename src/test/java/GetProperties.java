import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class GetProperties {

    private final Logger logger = LogManager.getLogger(this.getClass());

    public GetProperties() {
        Properties prop = new Properties();

        try (InputStream inputStream = GetProperties.class.getClassLoader().getResourceAsStream("config.properties")) {

            prop.load(inputStream);

            logger.info(prop.getProperty("server_ip_address"));

            prop.setProperty("server_ip_address", "192.168.1.10");

            logger.info(prop.getProperty("server_ip_address"));

        } catch (IOException e) {
            logger.error(e);
        }
    }

    public static void main(String[] args) {
        new GetProperties();
    }
}
