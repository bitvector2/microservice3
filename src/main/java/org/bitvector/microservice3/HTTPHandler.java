package org.bitvector.microservice3;

import static spark.Spark.get;

public class HTTPHandler {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello World!\n");
    }
}
