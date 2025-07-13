package com.rs;

import org.eclipse.jetty.server.Server;
import org.eclipse.jetty.servlet.ServletHandler;

public class Main {
    public static void main(String[] args) throws Exception {
        var server = new Server(8080);

        var handler = new ServletHandler();
        server.setHandler(handler);

        handler.addServletWithMapping(ReadingHandler.class, "/v1/sr");
        server.start();
        server.join();
    }
}