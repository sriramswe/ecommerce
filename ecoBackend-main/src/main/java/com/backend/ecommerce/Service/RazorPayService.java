package com.backend.ecommerce.Service;


import com.razorpay.Order;
import com.razorpay.RazorpayClient;
import lombok.RequiredArgsConstructor;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Currency;



@Service
@RequiredArgsConstructor
public class RazorPayService {

    // @Value("${razorpay.api.key}")
    private String apikey;

    //@Value("${razorpay.api.secret}")
    private String apiSecret;

    public String createOrder(int amount , int Currency , String receiptId) throws Exception{

        RazorpayClient razorpayClient = new RazorpayClient(apikey , apiSecret);
        JSONObject orders = new JSONObject();
        orders.put("amount" , amount *100);
        orders.put("currency" , Currency);
        orders.put("receiptId",receiptId);
        Order order = razorpayClient.orders.create(orders);
        return order.toString();
    }
}