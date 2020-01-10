package ru.netology.diploma.Db;

import lombok.val;

import java.sql.*;

public class DbInteraction {

    private static String url = System.getProperty("db.url");
    private static String user = "app";
    private static String password = "pass";


    public static String paymentStatus() throws SQLException{
        String sql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        String columnLabel = "status";
        String status;
        val conn = DriverManager.getConnection(url,user,password);
        val statement = conn.prepareStatement(sql);
        val rs = statement.executeQuery();
        rs.next();
        status = rs.getString(columnLabel);
        conn.close();
        return status;
    }

    public static String creditStatus() throws SQLException{
        String sql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        String columnLabel = "status";
        String status;
        val conn = DriverManager.getConnection(url,user,password);
        val statement = conn.prepareStatement(sql);
        val rs = statement.executeQuery();
        rs.next();
        status = rs.getString(columnLabel);
        conn.close();
        return status;
    }

}

