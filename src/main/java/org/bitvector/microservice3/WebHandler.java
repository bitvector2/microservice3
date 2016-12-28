package org.bitvector.microservice3;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.get;

public class WebHandler {
    private Logger logger;
    private Gson gson;
    private DbHandler dbHandler;

    WebHandler(DbHandler dbh) {
        logger = LoggerFactory.getLogger("org.bitvector.microservice3.WebHandler");
        logger.info("Starting WebHandler...");

        gson = new Gson();
        dbHandler = dbh;

        get("/hello", (request, response) -> {
            response.status(200);
            response.type("application/json");
            return gson.toJson(dbHandler.getAllProducts());
        });
    }

    public void close() {
        logger.info("Stopping WebHandler...");
    }
}
