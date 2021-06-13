package com.isuwang.bankenterpirse.properties;

import javax.servlet.ServletContext;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class ConfigContext {
    private static final String DIR_PATH = "dir.path";
    private static Properties properties = null;
    private static final String CONFIG_FILE_NAME = "config.properties";

    public static synchronized void load(ServletContext servletContext) {
        if (properties == null) {
            try {
                properties = new Properties();
                InputStream stream = servletContext.getResourceAsStream(CONFIG_FILE_NAME);
                properties.load(stream);
            } catch (IOException e) {
                System.err.println("配置文件加载失败");
                properties = null;
                e.printStackTrace();
            }
        }

    }

    public static String getDirPath() {
        return properties.getProperty(DIR_PATH);
    }
}
