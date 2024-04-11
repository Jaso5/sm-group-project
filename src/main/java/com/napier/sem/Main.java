package com.napier.sem;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        MySQLConnector connect = new MySQLConnector("root", "sem", "world");
        connect.connect();
    }
}