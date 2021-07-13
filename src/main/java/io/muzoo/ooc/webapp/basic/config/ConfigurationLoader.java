package io.muzoo.ooc.webapp.basic.config;

import java.io.FileInputStream;
import java.util.Properties;

public class ConfigurationLoader {

    public static ConfigProperties load(){
        String configFilename = "config.properties";
        try(FileInputStream fin = new FileInputStream(configFilename)) {
            Properties prop = new Properties();
            prop.load(fin);


            // get the property value and print it out

            String driverClassName = prop.getProperty("database.DriverClassName");
            String connectionUrl = prop.getProperty("database.connectionUrl");
            String username = prop.getProperty("database.username");
            String password = prop.getProperty("database.password");

            return new ConfigProperties.ConfigPropertiesBuilder()
                    .databaseDriverClassName(driverClassName)
                    .databaseConnectionUrl(connectionUrl)
                    .databaseUsername(username)
                    .databasePassword(password)
                    .build();

        } catch (Exception e) {
            return null;
        }
    }
}
