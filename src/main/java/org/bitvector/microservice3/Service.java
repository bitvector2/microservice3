package org.bitvector.microservice3;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class Service implements IClosable {
    private DBHandler dBHandler;
    private HTTPHandler hTTPHandler;

    Service() {
        CloseThread closeThread = new CloseThread(this);
        Runtime.getRuntime().addShutdownHook(closeThread);

        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        try (InputStream resourceStream = loader.getResourceAsStream("microservice3.properties")) {
            Properties props = new Properties(System.getProperties());
            props.load(resourceStream);
            System.setProperties(props);
        } catch (IOException e) {
            e.printStackTrace();
        }

        dBHandler = new DBHandler();
        hTTPHandler = new HTTPHandler(this.dBHandler);
    }

    public void close() {
        hTTPHandler.close();
        dBHandler.close();
    }
}
