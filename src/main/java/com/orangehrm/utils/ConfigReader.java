package com.orangehrm.utils;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public final class ConfigReader {

    private static final Properties PROPERTIES = new Properties();

    static {

        loadProperties("config.properties");

        String environment = PROPERTIES.getProperty("environment");

        if (environment == null || environment.trim().isEmpty()) {

            throw new RuntimeException(
                    "Environment is missing in config.properties."
            );
        }

        String environmentConfig =
                "config/" + environment.trim().toLowerCase() + ".properties";

        loadProperties(environmentConfig);
    }

    private ConfigReader() {

        // Utility class - prevent object creation
    }

    // ============================================================
    // LOAD PROPERTIES
    // ============================================================

    private static void loadProperties(String resourcePath) {

        try (InputStream inputStream =
                     ConfigReader.class
                             .getClassLoader()
                             .getResourceAsStream(resourcePath)) {

            if (inputStream == null) {

                throw new RuntimeException(
                        "Configuration file not found: "
                                + resourcePath
                );
            }

            PROPERTIES.load(inputStream);

        } catch (IOException e) {

            throw new RuntimeException(
                    "Unable to load configuration file: "
                            + resourcePath,
                    e
            );
        }
    }

    // ============================================================
    // GET STRING PROPERTY
    // ============================================================

    public static String get(String key) {

        String value =
                PROPERTIES.getProperty(key);

        if (value == null ||
                value.trim().isEmpty()) {

            throw new RuntimeException(
                    "Configuration key is missing or empty: "
                            + key
            );
        }

        return value.trim();
    }

    // ============================================================
    // GET INTEGER PROPERTY
    // ============================================================

    public static int getInt(String key) {

        try {

            return Integer.parseInt(
                    get(key)
            );

        } catch (NumberFormatException e) {

            throw new RuntimeException(
                    "Configuration value must be an integer for key: "
                            + key,
                    e
            );
        }
    }

    // ============================================================
    // GET BOOLEAN PROPERTY
    // ============================================================

    public static boolean getBoolean(String key) {

        return Boolean.parseBoolean(
                get(key)
        );
    }
}