package com.napier.sem;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

public class HttpAPI implements HttpHandler {
    @Override
    public void handle(HttpExchange http) throws IOException {
        System.out.println("Received request!");
        if ("POST".equals(http.getRequestMethod())) {
            // Handle request
            this.sendResponse(http, 200, "TEST\n");
        } else {
            // Error
            this.sendResponse(http, 400, "Bad Request\n");
        }
    }

    private void sendResponse(HttpExchange http, int response, String content) throws IOException {
        OutputStream stream = http.getResponseBody();
        http.sendResponseHeaders(response, content.length());
        stream.write(content.getBytes(StandardCharsets.UTF_8));
        stream.flush();
        stream.close();
    }
}
