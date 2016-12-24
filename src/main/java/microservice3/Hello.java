package microservice3;

import static spark.Spark.get;

public class Hello {
    public static void main(String[] args) {
        get("/hello", (req, res) -> "Hello");
    }
}
