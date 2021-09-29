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
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Map;
import org.json.HTTP;
import org.json.JSONException;

import org.json.JSONObject;

/**
 *
 * @author root
 */
@WebServlet("/createorder")
public class CreateOrder extends HttpServlet {

    String key = "rzp_test_hyRWe2OzpfSajo";
    String secret = "EhNJX9giFfSxLMPchEVkIzUP";

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        StringBuffer jb = new StringBuffer();
        String line = null;
        try {
            BufferedReader reader = req.getReader();
            while ((line = reader.readLine()) != null) {
                jb.append(line);
            }
        } catch (Exception e) {
            /*report an error*/ }
        JSONObject jsonObject;
        try {
            jsonObject = HTTP.toJSONObject(jb.toString());

        } catch (JSONException e) {
            // crash and burn
            throw new IOException("Error parsing JSON request string");
        }
        String temp=  jsonObject.getString("Method");
        JSONObject reqJSONObject = new JSONObject(temp);
        resp.setContentType("application/json");
        PrintWriter out = resp.getWriter();
//        out.println(reqJSONObject);
        try {
            Integer amount = Integer.parseInt(reqJSONObject.getString("amount"));
            if (amount == null) {
                System.out.println("amount is null");
            }
//            System.out.println(amount);

            RazorpayClient razorpayClient = new RazorpayClient(key, secret);
            JSONObject options = new JSONObject();
            options.put("amount", amount*100);
            options.put("currency", "INR");
            options.put("receipt", "txn_123456");
            Order order = razorpayClient.Orders.create(options);
            System.out.println(amount);
            System.out.println(order);
            out.println(order.toJson());
            // if you want you can save this order in our database
        } catch (Exception e) {
            out.println("enter valid number : <br>Exception name" + e.getClass() + "<br>Message: " + e.getMessage());
            System.err.println(e.getMessage());

        }
    }

}
