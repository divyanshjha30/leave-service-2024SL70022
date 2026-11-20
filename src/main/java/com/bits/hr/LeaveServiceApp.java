package com.bits.hr;

import com.sun.net.httpserver.HttpServer;

import java.io.IOException;
import java.io.OutputStream;
import java.net.InetSocketAddress;
import java.nio.charset.StandardCharsets;

/**
 * Runnable entry point for the Employee Leave Service - DIVYANSH JHA 2024SL70022.
 *
 * Serves the leave calculation over HTTP so the container can be verified with curl.
 * The environment name comes from APP_ENV, which is how the same image reports
 * itself differently in staging and production.
 */
public class LeaveServiceApp {

    static final int PORT = 8080;

    static String report(String env) {
        LeaveCalculator calculator = new LeaveCalculator();
        int balance = calculator.leaveBalance(30, 12);
        double encashment = calculator.leaveEncashment(balance, 60000);
        return "Employee Leave Service - DIVYANSH JHA 2024SL70022\n"
                + "Environment      : " + env + "\n"
                + "Leave balance    : " + balance + " days\n"
                + "Leave encashment : Rs. " + encashment + "\n";
    }

    public static void main(String[] args) throws IOException {
        String env = System.getenv().getOrDefault("APP_ENV", "local");
        HttpServer server = HttpServer.create(new InetSocketAddress(PORT), 0);
        server.createContext("/", exchange -> {
            byte[] body = report(env).getBytes(StandardCharsets.UTF_8);
            exchange.getResponseHeaders().add("Content-Type", "text/plain; charset=utf-8");
            exchange.sendResponseHeaders(200, body.length);
            try (OutputStream os = exchange.getResponseBody()) {
                os.write(body);
            }
        });
        server.start();
        System.out.println("Leave Service started on port " + PORT + " [APP_ENV=" + env + "]");
    }
}
