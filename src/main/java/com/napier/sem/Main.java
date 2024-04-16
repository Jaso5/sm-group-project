package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        MySQLConnector connector = new MySQLConnector("root", "sem", "world");
        Connection con = connector.getCon();
        ResultSet rs = new Query().addReg1(Region.COUNTRY).isPopulation(true).execute(null);

    }
}