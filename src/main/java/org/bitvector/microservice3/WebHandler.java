package org.bitvector.microservice3;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class WebHandler {
    private Logger logger;
    private Gson gson;
    private DbHandler dbHandler;

    WebHandler(DbHandler dbh) {
        logger = LoggerFactory.getLogger("org.bitvector.microservice3.WebHandler");
        logger.info("Starting WebHandler...");
        gson = new Gson();
        dbHandler = dbh;

        ipAddress(System.getProperty("org.bitvector.microservice3.listen-address"));
        port(Integer.parseInt(System.getProperty("org.bitvector.microservice3.listen-port")));
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
        return gson.toJson(dbHandler.getAllProducts());
    }

    private String getProductById(Request request, Response response) {
        response.status(200);
        response.type("application/json");
        Integer id = Integer.parseInt(request.params(":ID"));
        return gson.toJson(dbHandler.getProductById(id));
    }

    private String putProductById(Request request, Response response) {
        response.status(200);
        response.type("application/json");
        ProductEntity product = gson.fromJson(request.body(), ProductEntity.class);
        product.setId(Integer.parseInt(request.params(":ID")));
        return gson.toJson(dbHandler.updateProduct(product));
    }

    private String postProduct(Request request, Response response) {
        response.status(200);
        response.type("application/json");
        ProductEntity product = gson.fromJson(request.body(), ProductEntity.class);
        return gson.toJson(dbHandler.saveProduct(product));
    }

    private String deleteProductById(Request request, Response response) {
        response.status(200);
        response.type("application/json");
        ProductEntity product = new ProductEntity();
        product.setId(Integer.parseInt(request.params(":ID")));
        return gson.toJson(dbHandler.deleteProduct(product));
    }
}
