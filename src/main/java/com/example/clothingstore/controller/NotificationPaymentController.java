package com.example.clothingstore.controller;

import com.example.clothingstore.service.impl.PaymentServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping()
public class NotificationPaymentController {
    @Autowired
    PaymentServiceImpl paymentService;

    @GetMapping("/profile")
    public ResponseEntity<?> returnResultPayment(HttpServletRequest request) throws URISyntaxException {
        if (request.getParameter("vnp_ResponseCode").equals("00"))
        {
            paymentService.acceptOrder(Long.valueOf(request.getParameter("vnp_TxnRef")));
        }
        else{
            paymentService.cancelOrder(Long.valueOf(request.getParameter("vnp_TxnRef")));
        }
        URI urlPayment = new URI("http://localhost:3000/profile?tab=orders");
        HttpHeaders httpHeaders = new HttpHeaders();
        httpHeaders.setLocation(urlPayment);
        return new ResponseEntity<>(httpHeaders, HttpStatus.SEE_OTHER);
    }



}
