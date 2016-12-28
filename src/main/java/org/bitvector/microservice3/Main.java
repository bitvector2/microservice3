package org.bitvector.microservice3;

public class Main {
    public static void main(String[] args) {
        DbHandler dbHandler = new DbHandler();
        WebHandler webHandler = new WebHandler(dbHandler);

        // webHandler.close();
        // dbHandler.close();
    }
}
