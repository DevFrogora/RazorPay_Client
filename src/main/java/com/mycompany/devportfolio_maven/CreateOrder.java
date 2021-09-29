/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.mycompany.devportfolio_maven;

import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import org.json.JSONObject;

/**
 *
 * @author root
 */
@WebServlet("/createorder")
public class CreateOrder extends HttpServlet{

    String key = "rzp_test_hyRWe2OzpfSajo";
    String secret = "EhNJX9giFfSxLMPchEVkIzUP";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setContentType("text/html");
        PrintWriter out = resp.getWriter();
        out.println("<h2>Welcome to servlet</h2>");
        try {
            Integer amount = Integer.parseInt(req.getParameter("amount"));
            if (amount == null) {
                out.println("amount is null");
            }
            out.println(amount);

            RazorpayClient razorpayClient = new RazorpayClient(key, secret);
            JSONObject options = new JSONObject();
            options.put("amount", amount*100);
            options.put("currency", "INR");
            options.put("receipt", "txn_123456");
            Order order = razorpayClient.Orders.create(options);
            System.out.println(amount);
            System.out.println(order);

        } catch (Exception e) {
            out.println("enter valid number : <br>Exception name" + e.getClass() + "<br>Message: " + e.getMessage());
            System.err.println(e.getMessage());

        }
    }

}
