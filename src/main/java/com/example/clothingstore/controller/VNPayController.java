package com.example.clothingstore.controller;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.config.OrderCreator;
import com.example.clothingstore.config.PaymentConfig;
import com.example.clothingstore.dto.PaymentDTO;
import com.example.clothingstore.dto.PaymentResultDTO;
import com.example.clothingstore.model.*;
import com.example.clothingstore.repository.OrderRepository;
import com.example.clothingstore.repository.ProductRepository;
import com.example.clothingstore.repository.TypeRepository;
import com.example.clothingstore.repository.UserRepository;
import com.example.clothingstore.security.principal.UserDetailService;
import com.example.clothingstore.service.AddressService;
import com.example.clothingstore.service.CartProductService;
import com.example.clothingstore.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping()
public class VNPayController {
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    TransactionServiceImpl orderDetailService;
    @Autowired
    TypeRepository typeRepository;
    @Autowired
    ProductRepository productRepository;
    @Autowired
    PaymentServiceImpl paymentService;
    @Autowired
    OrderRepository orderRepository;
    @Autowired
    UserRepository userRepository;
    @Autowired
    AddressServiceImpl addressService;
    @Autowired
    UserServiceImpl userService;
    @Autowired
    CartProductServiceImpl cartProductService;
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    TypeServiceImpl typeService;

    @PostMapping("/user/order/payment/vnpay")
    public ResponseEntity<?> createPaymentVnpay(@RequestBody List<Object> req) throws UnsupportedEncodingException, NoSuchAlgorithmException, ParseException {
        OrderEntity orderEntity = OrderCreator.createOrder(userDetailService, productService, typeService, addressService, orderDetailService, orderService, cartProductService, req, LocalVariable.pendingMessage);

        PaymentDTO paymentDTO = new PaymentDTO();
        paymentDTO.setOrderId(orderEntity.getId());
        paymentDTO.setBankCode(PaymentConfig.BANKCODE);
        paymentDTO.setAmount((int) (Math.toIntExact(orderEntity.getTotalPrice())+orderEntity.getShippingFee()));
        paymentDTO.setDescription(LocalVariable.pendingMessage);

        Map<String, String> vnp_Params = paymentService.returnParamVnPay(paymentDTO);
        String paymentUrl = paymentService.returnPaymentUrl(vnp_Params);
        PaymentResultDTO paymentResultDTO = new PaymentResultDTO("00", "Success", paymentUrl);
        return ResponseEntity.status(HttpStatus.OK).body(paymentResultDTO);
    }
}
