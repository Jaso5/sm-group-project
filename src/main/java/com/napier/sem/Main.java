package com.napier.sem;

import java.sql.Connection;
import java.sql.ResultSet;

public class Main {
    public static void main(String[] args) {
        System.out.println("Hello and welcome!");
        MySQLConnector connector = new MySQLConnector("root", "sem", "world");
        Connection con = connector.getCon();
        ResultSet rs1 = new Query().addReg1(Region.COUNTRY).execute(con);
        ResultSet rs2 = new Query().addReg1(Region.REGION).execute(con);
        ResultSet rs3 = new Query().addReg1(Region.CITY).addReg2(Region.COUNTRY, "Germany").execute(con);
        System.out.println("Received result table!");
    }
}