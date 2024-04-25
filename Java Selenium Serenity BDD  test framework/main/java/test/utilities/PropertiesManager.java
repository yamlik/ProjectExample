package com..test.utilities;

import org.apache.commons.io.FileUtils;

import java.io.File;
import java.io.IOException;
import java.util.Properties;

public class PropertiesManager {

    private static PropertiesManager propertiesManager;
    private Properties properties;

    private PropertiesManager() {
        fileProcessor();
    }

    private void fileProcessor() {
        //String path = System.getProperty("user.dir") + "/src/main/resources";
        String path = System.getProperty("user.dir") + "/target/classes";
        File directory = new File(path);
        File[] files = directory.listFiles();
        properties = new Properties();
        for(File file: files) {
            if(file.getName().endsWith(".properties")) {
                try {
                    properties.load(FileUtils.openInputStream(file));
                } catch (IOException ex) {

                }
            }
        }
        properties.putAll(System.getProperties());
        System.setProperties(properties);
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }

    public static PropertiesManager get() {
        if(propertiesManager == null) {
            propertiesManager = new PropertiesManager();
        }
        return propertiesManager;
    }
}
