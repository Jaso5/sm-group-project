package com.napier.sem;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;

/**
 * A HttpHandler for serving files in a directory
 */

public class FileServer implements HttpHandler {
    private final String path;

    /**
     * @param path Path of a folder of files to server
     */
    public FileServer(String path) {
        this.path = path;
    }

    @Override
    public void handle(HttpExchange http) {
        if ("GET".equals(http.getRequestMethod())) {
            // Serve file
            URI uri = http.getRequestURI();

            String path = this.path.concat(uri.getPath());

            if ("/".equals(uri.getPath())) {
                path = this.path.concat("/index.html");
            }

            System.out.println(uri.getPath());

            File file = new File(path);

            try (FileInputStream in_stream = new FileInputStream(file)) {
                OutputStream out_stream = http.getResponseBody();
                http.sendResponseHeaders(200, file.length());
                in_stream.transferTo(out_stream);
                out_stream.flush();
                out_stream.close();
            } catch (IOException _e) {
                try {
                    sendResponse(http, 404, "File not found");
                } catch (IOException ignore) { }
            }
        } else {
            try {
                sendResponse(http, 400, "");
            } catch (IOException ignore) { }
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
