package config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ApiKey {
    public static String getApiKey() {
        Properties properties = new Properties();

        try (FileInputStream input = new FileInputStream("src/main/resources/application.yaml")) {
            properties.load(input);
        } catch (IOException e) {
            e.printStackTrace();
        }

        return properties.getProperty("OPENAI_API_KEY");
    }
}
