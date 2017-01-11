package org.bitvector.microservice3;

import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import spark.Request;
import spark.Response;

import static spark.Spark.*;

public class HTTPHandler implements IClosable {
    private Logger logger;
    private Gson gson;
    private DBHandler dBHandler;

    HTTPHandler(DBHandler dBHandler) {
        logger = LoggerFactory.getLogger("org.bitvector.microservice3.HTTPHandler");
        logger.info("Starting HTTPHandler...");
        gson = new Gson();
        this.dBHandler = dBHandler;

        ipAddress(System.getProperty("org.bitvector.microservice3.listen-address"));
        port(Integer.parseInt(System.getProperty("org.bitvector.microservice3.listen-port")));
        get("/products", this::getAllProducts);
        get("/products/:ID", this::getProductById);
        put("/products/:ID", this::putProductById);
        post("/products", this::postProduct);
        delete("/products/:ID", this::deleteProductById);
    }

    public void close() {
        logger.info("Stopping HTTPHandler...");
        stop();
    }

    private String getAllProducts(Request request, Response response) {
        response.status(200);
        response.type("application/json");
        return gson.toJson(dBHandler.getAllProducts());
    }

    private String getProductById(Request request, Response response) {
        response.status(200);
        response.type("application/json");
        Integer id = Integer.parseInt(request.params(":ID"));
        return gson.toJson(dBHandler.getProductById(id));
    }

    private String putProductById(Request request, Response response) {
        response.status(200);
        response.type("application/json");
        ProductEntity product = gson.fromJson(request.body(), ProductEntity.class);
        product.setId(Integer.parseInt(request.params(":ID")));
        return gson.toJson(dBHandler.updateProduct(product));
    }

    private String postProduct(Request request, Response response) {
        response.status(200);
        response.type("application/json");
        ProductEntity product = gson.fromJson(request.body(), ProductEntity.class);
        return gson.toJson(dBHandler.saveProduct(product));
    }

    private String deleteProductById(Request request, Response response) {
        response.status(200);
        response.type("application/json");
        ProductEntity product = new ProductEntity();
        product.setId(Integer.parseInt(request.params(":ID")));
        return gson.toJson(dBHandler.deleteProduct(product));
    }
}
