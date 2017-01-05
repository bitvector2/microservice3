package org.bitvector.microservice3;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import java.io.IOException;

import static spark.Spark.*;

public class WebHandler {
    private Logger logger;
    private ObjectMapper mapper;
    private DbHandler dbHandler;

    WebHandler(DbHandler dbh) {
        logger = LoggerFactory.getLogger("org.bitvector.microservice3.WebHandler");
        logger.info("Starting WebHandler...");
        mapper = new ObjectMapper();
        dbHandler = dbh;

        // Route Wiring
        get("/products", this::getAllProducts);
        get("/products/:ID", this::getProductById);
        put("/products/:ID", this::putProductById);
        post("/products", this::postProduct);
        delete("/products/:ID", this::deleteProductById);
    }

    public void close() {
        logger.info("Stopping WebHandler...");
        stop();
    }

    private String getAllProducts(Request request, Response response) {
        response.status(200);
        response.type("application/json");
        String body = "";
        try {
            body = mapper.writeValueAsString(dbHandler.getAllProducts());
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return body;
    }

    private String getProductById(Request request, Response response) {
        response.status(200);
        response.type("application/json");
        Integer id = Integer.parseInt(request.params(":ID"));
        String body = "";
        try {
            body = mapper.writeValueAsString(dbHandler.getProductById(id));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return body;
    }

    private String putProductById(Request request, Response response) {
        response.status(200);
        response.type("application/json");
        ProductEntity product = null;
        try {
            product = mapper.readValue(request.body(), ProductEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String body = "";
        if(product != null) {
            product.setId(Integer.parseInt(request.params(":ID")));
            try {
                body = mapper.writeValueAsString(dbHandler.updateProduct(product));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }}
        return body;
    }

    private String postProduct(Request request, Response response) {
        response.status(200);
        response.type("application/json");
        ProductEntity product = null;
        try {
            product = mapper.readValue(request.body(), ProductEntity.class);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String body = "";
        if(product != null) {
            try {
                body = mapper.writeValueAsString(dbHandler.saveProduct(product));
            } catch (JsonProcessingException e) {
                e.printStackTrace();
            }}
        return body;
    }

    private String deleteProductById(Request request, Response response) {
        response.status(200);
        response.type("application/json");
        ProductEntity product = new ProductEntity();
        product.setId(Integer.parseInt(request.params(":ID")));
        String body = "";
        try {
            body = mapper.writeValueAsString(dbHandler.deleteProduct(product));
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return body;
    }
}
