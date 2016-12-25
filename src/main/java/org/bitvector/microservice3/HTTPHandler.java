package org.bitvector.microservice3;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static spark.Spark.get;

public class HTTPHandler {
    public static void main(String[] args) {
        Logger logger = LoggerFactory.getLogger("org.bitvector.microservice3.HTTPHandler");
        logger.info("Starting HTTPHandler...");

        DBHandler dbHandler = new DBHandler();
        Gson gson = new Gson();

        get("/hello", (request, response) -> {
            response.status(200);
            response.type("application/json");
            return gson.toJson(dbHandler.getAllProducts());
        });

        // dbHandler.close();
    }
}
