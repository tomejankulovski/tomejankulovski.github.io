package com.houseapi.configurations;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Arrays;
import java.util.Objects;
import java.util.function.Consumer;

public class Properties {
	private static java.util.Properties props;

    static {
        props = new java.util.Properties();

        String pathWithPropertiesFiles = System.getProperty("configuration.path");
        String[] paths = pathWithPropertiesFiles.split("[;]");

        Arrays.asList(paths).forEach(new Consumer<String>() {
			public void accept(String propertyPath) {
				Arrays.asList(Objects.requireNonNull(new File(propertyPath).listFiles())).forEach(new Consumer<File>() {
					public void accept(File propertyFile) {
					    InputStream input;
					    try {
					        input = new FileInputStream(propertyFile);
					        props.load(input);
					    } catch (IOException e) {
					        throw new RuntimeException(e);
					    }
					}
				});
			}
		});
    }

    public static String getValue(String key) {
        String envProperty = System.getenv(key);
        if (envProperty != null && !envProperty.equals("null")) {
            return envProperty;
        }

        String systemProperty = System.getProperty(key);
        if (systemProperty != null && !systemProperty.equals("null")) {
            return systemProperty;
        }

        return props.getProperty(key);
    }

}
