package com.example.clothingstore.service.impl;

import com.example.clothingstore.config.PaymentConfig;
import com.example.clothingstore.dto.PaymentDTO;
import com.example.clothingstore.repository.ProductRepository;
import com.example.clothingstore.repository.TypeRepository;
import com.example.clothingstore.repository.UserRepository;
import com.example.clothingstore.service.AddressService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class PaymentServiceImpl {
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    TransactionServiceImpl orderDetailService;
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressService addressService;

    public Map<String, String> returnParamVnPay(PaymentDTO paymentDTO)
    {
        int amount = paymentDTO.getAmount()*100;
        Map<String, String> vnp_Params = new HashMap<>();
        vnp_Params.put("vnp_Version", PaymentConfig.VERSIONVNPAY);
        vnp_Params.put("vnp_Command", PaymentConfig.COMMAND);
        vnp_Params.put("vnp_TmnCode", PaymentConfig.TMNCODE);
        vnp_Params.put("vnp_Amount", String.valueOf(amount));
        vnp_Params.put("vnp_CurrCode", PaymentConfig.CURRCODE);
        vnp_Params.put("vnp_BankCode", paymentDTO.getBankCode());
        vnp_Params.put("vnp_TxnRef", String.valueOf(paymentDTO.getOrderId()));
        vnp_Params.put("vnp_OrderInfo", paymentDTO.getDescription());
        vnp_Params.put("vnp_OrderType", PaymentConfig.ORDERTYPE);
        vnp_Params.put("vnp_Locale", PaymentConfig.LOCALEDEFAULT);
        vnp_Params.put("vnp_ReturnUrl", PaymentConfig.RETURNURL); //add id order
        vnp_Params.put("vnp_IpAddr", PaymentConfig.IPDEFAULT);
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
        String vnp_CreateDate = format.format(date);
        vnp_Params.put("vnp_CreateDate", vnp_CreateDate);
        return vnp_Params;
    }

    public String returnPaymentUrl(Map<String, String> vnp_Params) throws UnsupportedEncodingException {
        List fieldNames = new ArrayList(vnp_Params.keySet());
        Collections.sort(fieldNames);
        StringBuilder hashData = new StringBuilder();
        StringBuilder query = new StringBuilder();
        Iterator itr = fieldNames.iterator();
        while (itr.hasNext()) {
            String fieldName = (String) itr.next();
            String fieldValue = (String) vnp_Params.get(fieldName);
            if ((fieldValue != null) && (fieldValue.length() > 0)) {
                //Build hash data
                hashData.append(fieldName);
                hashData.append('=');
                hashData.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                //Build query
                query.append(URLEncoder.encode(fieldName, StandardCharsets.US_ASCII.toString()));
                query.append('=');
                query.append(URLEncoder.encode(fieldValue, StandardCharsets.US_ASCII.toString()));
                if (itr.hasNext()) {
                    query.append('&');
                    hashData.append('&');
                }
            }
        }
        String vnp_SecureHash = PaymentConfig.hmacSHA512(PaymentConfig.CHECKSUM, hashData.toString());
        String queryUrl = query.toString() + "&vnp_SecureHash=" + vnp_SecureHash;
        String paymentUrl = PaymentConfig.VNPAYURL + "?" + queryUrl;
        return paymentUrl;
    }

}
