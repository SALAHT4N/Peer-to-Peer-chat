package org.example;

import java.io.IOException;

public class Main {
    public static void main(String[] args) throws IOException {
       Server server = Server.getInstance(8001);

       server.start();

    }
}