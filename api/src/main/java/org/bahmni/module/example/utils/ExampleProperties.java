package org.bahmni.module.example.utils;

import org.openmrs.util.OpenmrsUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ExampleProperties {
    private static Properties properties;

    public static void load() {
        File file = new File(OpenmrsUtil.getApplicationDataDirectory(), "example.properties");
        if (file.exists()) {
            String propertyFile = file.getAbsolutePath();
            try {
                properties = new Properties(System.getProperties());
                properties.load(new FileInputStream(propertyFile));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static String getProperty(String key) {
        if (properties != null) {
            return properties.getProperty(key);
        }
        return null;
    }

    public static void initalize(Properties props) {
        properties = props;
    }

}
