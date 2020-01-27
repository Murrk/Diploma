package ru.netology.diploma.db;

import lombok.val;
import org.apache.commons.dbutils.QueryRunner;

import java.sql.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class DbInteraction {
    private static String url = System.getProperty("db.url");
    private static String user = "app";
    private static String password = "pass";

    public static String paymentStatus() {
        String sql = "SELECT status FROM payment_entity ORDER BY created DESC LIMIT 1;";
        String columnLabel = "status";
        return compareStatus(sql, columnLabel);
    }

    public static String creditStatus() {
        String sql = "SELECT status FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        String columnLabel = "status";
        return compareStatus(sql, columnLabel);
    }

    public static boolean checkCreditId() {
        String sqlCredit = "SELECT bank_id FROM credit_request_entity ORDER BY created DESC LIMIT 1;";
        String columnLabelCredit = "bank_id";
        String sqlOrder = "SELECT credit_id FROM order_entity ORDER BY created DESC LIMIT 1;";
        String columnLabelOrder = "credit_id";
        assertEquals(compareStatus(sqlCredit, columnLabelCredit), compareStatus(sqlOrder, columnLabelOrder));
        return true;
    }

    public static boolean checkPaymentId() {
        String sqlPayment = "SELECT transaction_id FROM payment_entity ORDER BY created DESC LIMIT 1;";
        String columnLabelPayment = "transaction_id";
        String sqlOrder = "SELECT payment_id FROM order_entity ORDER BY created DESC LIMIT 1;";
        String columnLabelOrder = "payment_id";
        assertEquals(compareStatus(sqlPayment, columnLabelPayment), compareStatus(sqlOrder, columnLabelOrder));
        return true;
    }

    private static String compareStatus(String sql, String columnLabel) {
        String status = "";
            try (val conn = DriverManager.getConnection(url, user, password)) {
                val statement = conn.prepareStatement(sql);
                val rs = statement.executeQuery();
                rs.next();
                status = rs.getString(columnLabel);
            } catch (SQLException e) {
                e.printStackTrace();
            }
            return status;
    }

    public static void clearDb() {
        val runner = new QueryRunner();
        try (val conn = DriverManager.getConnection(url, user, password)) {
            runner.update(conn, "DELETE FROM credit_request_entity;");
            runner.update(conn, "DELETE FROM order_entity;");
            runner.update(conn, "DELETE FROM payment_entity;");
        } catch (SQLException e) {
        e.printStackTrace();
        }
    }

}

