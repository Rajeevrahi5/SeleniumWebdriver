package utility;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Helper {
    public String readDataFromFile(String propertyKey) {
        String propertyValue;
        try {
            InputStream input = new FileInputStream("src/test/resources/test_data/onboarding.properties");
            Properties prop = new Properties();

            // load a properties file
            prop.load(input);
            propertyValue = prop.getProperty(propertyKey);

        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return propertyValue;
    }
}
