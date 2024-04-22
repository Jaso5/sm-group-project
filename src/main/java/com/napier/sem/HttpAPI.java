package com.napier.sem;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import org.json.JSONObject;
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
            String body = http.getRequestBody().toString();
            JSONObject obj = new JSONObject(body);

            String q = obj.getString("q");

            if (q.equals("m0")) {
                // Get population of name with size
                String name = obj.getString("e");
                String as = obj.getString("as");


                int population = 0;
                sendResponse(http, 200, String.format("{pop: '%s'}", population));
            } else if (q.equals("p0")) {
                // Get total population
                int population = 0;
                sendResponse(http, 200, String.format("{pop: '%s'}", population));
            } else {
                // Run query Q, with extra data maybe, see two lines below
//                String as = obj.getString("as");
//                int N = obj.getInt("e");

                String filename = "style.css";
                sendResponse(http, 200, String.format("{file: '%s'}", filename));
            }
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
