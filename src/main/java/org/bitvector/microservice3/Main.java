package org.bitvector.microservice3;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Main {
    public static void main(String[] args) {
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream resourceStream = loader.getResourceAsStream("microservice3.properties")) {
            Properties props = new Properties(System.getProperties());
            props.load(resourceStream);
            System.setProperties(props);
        } catch (IOException e) {
            e.printStackTrace();
        }

        DbHandler dbHandler = new DbHandler();
        WebHandler webHandler = new WebHandler(dbHandler);

        // webHandler.close();
        // dbHandler.close();
        // System.exit(0);
    }
}
