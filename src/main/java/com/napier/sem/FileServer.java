package com.napier.sem;

import com.sun.net.httpserver.HttpExchange;
import com.sun.net.httpserver.HttpHandler;

import java.io.*;
import java.net.URI;
import java.nio.charset.StandardCharsets;


public class FileServer implements HttpHandler {
    private String path;
    public FileServer(String path) {
        this.path = path;
    }
    @Override
    public void handle(HttpExchange http) throws IOException {
        if ("GET".equals(http.getRequestMethod())) {
            // Serve file
            URI uri = http.getRequestURI();

            String path = this.path.concat(uri.getPath());

            if ("/".equals(uri.getPath())) {
                path = this.path.concat("/index.html");
            }

            System.out.println(uri.getPath());

            File file = new File(path);

            try (FileInputStream istream = new FileInputStream(file)) {
                OutputStream ostream = http.getResponseBody();
                http.sendResponseHeaders(200, file.length());
                istream.transferTo(ostream);
                ostream.flush();
                ostream.close();
            } catch (FileNotFoundException _e) {
                sendResponse(http, 404, "");
            }
        } else {
            sendResponse(http, 400, "");
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
