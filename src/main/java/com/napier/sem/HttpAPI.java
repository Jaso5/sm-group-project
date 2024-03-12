package com.napier.sem;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;

/**
 * The API handler for our program
 */
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

    /**
     * Return a response for the request
     * @param http HTTP request to respond to
     * @param response HTTP response code
     * @param content String to send back
     * @throws IOException In case of failure to send response
     */
    private void sendResponse(HttpExchange http, int response, String content) throws IOException {
        OutputStream stream = http.getResponseBody();
        http.sendResponseHeaders(response, content.length());
        stream.write(content.getBytes(StandardCharsets.UTF_8));
        stream.flush();
        stream.close();
    }
}
