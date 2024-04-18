package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.net.InetSocketAddress;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * Program Entrypoint
 * This runs a HTTP server and registers our handlers
 */

public class Main {
    public static void main(String[] args) throws IOException {
        MySQLConnector connector = new MySQLConnector("root", "sem", "world");
      
        HttpServer server = HttpServer.create(new InetSocketAddress("localhost", 80), 0);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) Executors.newFixedThreadPool(10);

        server.createContext("/api", new HttpAPI());
        server.createContext("/", new FileServer("web"));
        server.setExecutor(executor);
        System.out.println("Starting server!");
        server.start();
    }
}