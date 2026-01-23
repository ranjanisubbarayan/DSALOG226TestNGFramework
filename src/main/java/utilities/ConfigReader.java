package utilities;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static final Properties prop = new Properties();

    static {
        try (FileInputStream fis = new FileInputStream(
                System.getProperty("user.dir") +
                "/src/test/resources/properties/config.properties")) {

            prop.load(fis);

        } catch (IOException e) {
            throw new RuntimeException(
                    "Failed to load config.properties from src/test/resources/properties", e);
        }
    }

 
    public static String getProperty(String key) {        
        return prop.getProperty(key);
    }

 
    public static String getBrowser() {
        return getProperty("browser").toLowerCase().trim();
    }
}
