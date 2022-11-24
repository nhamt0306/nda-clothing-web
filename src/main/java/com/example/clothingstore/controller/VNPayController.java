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
        OrderEntity orderEntity = OrderCreator.createOrder(userDetailService, productService, typeService, addressService, orderDetailService, orderService, cartProductService, req, LocalVariable.deliveringMessage);

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



    public Object createOrder(@RequestBody List<Object> req) throws ParseException {
        // get request data
        List<Map<String, String>> orderDetailsEntityListReq = (List<Map<String, String>>) req.get(0);
        Map<String, String> orderInformation = (Map<String, String>) req.get(1);
        // create variable for usage
        OrderEntity orderEntity;
        // get user information
        UserEntity user = userDetailService.getCurrentUser();
        long totalcost = Long.valueOf(0);

        List<TransactionEntity> transactionEntities = new ArrayList<TransactionEntity>();
        for (Map<String, String> transaction : orderDetailsEntityListReq)
        {
            long unitPrice = Long.parseLong(transaction.get("unit_price"));
            long quantity = Long.parseLong(transaction.get("quantity"));
            long size = Long.parseLong(transaction.get("size"));
            String color = transaction.get("color");

            ProductEntity productEntity = productService.findProductById(Long.parseLong(transaction.get("product_id")));
            TransactionEntity transactionEntity = new TransactionEntity(unitPrice, quantity, color, size, productEntity);
            TypeEntity typeEntity = typeService.getTypeByColorAndSizeAndProductId(color, size, productEntity.getId());
            if (quantity > typeEntity.getQuantity())
            {
                return "Dont enought quantity of "+ productEntity.getName();
            }
//            // update type entity
//            typeEntity.setSold(typeEntity.getSold() + quantity);
//            typeEntity.setQuantity(typeEntity.getQuantity() - quantity);

            totalcost += unitPrice*quantity;
            transactionEntities.add(transactionEntity);
        }
        // map value for orderEntity and check if user want to add new address
        if (orderInformation.get("address") != null) {
            orderEntity = new OrderEntity(totalcost, orderInformation.get("note") == null ? "" : orderInformation.get("note"), Long.parseLong(orderInformation.get("shipping_fee") == null ? "25000" : orderInformation.get("shipping_fee")), orderInformation.get("payment") == null ? "COD" : orderInformation.get("payment"), "PENDING" , user.getFullname() ,user.getAddress(), user.getPhone());
        } else
        {
            AddressEntity address = addressService.getAddressDefaultOfUser(user.getId());
            orderEntity = new OrderEntity(totalcost, orderInformation.get("note") == null ? address.getNote() : orderInformation.get("note"), Long.valueOf(25000) ,"PENDING" , "COD", address.getName(), address.getAddress(), address.getPhoneNumber());
            if (totalcost > Long.valueOf(250000L))
            {
                orderEntity.setShippingFee(Long.valueOf(0L));
            }

        }
        orderEntity.setUserEntity(user);
        System.out.println(orderEntity);
        // map order and order-details
        orderEntity.setOrderDetailsEntities(transactionEntities);
        orderEntity.setCreate_at(new Timestamp(System.currentTimeMillis()));
        orderEntity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
        transactionEntities.forEach(i -> i.setOrderEntity(orderEntity));
        // insert order and details to DB
        orderService.addNewOrder(orderEntity);
        // delete cartProduct from DB
        for (TransactionEntity transactionEntity: transactionEntities)
        {
            transactionEntity.setCreate_at(new Timestamp(System.currentTimeMillis()));
            transactionEntity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
            orderDetailService.save(transactionEntity);
            cartProductService.deleteProductInCart(user.getId(), transactionEntity.getProductEntity().getId(), transactionEntity.getColor(), transactionEntity.getSize());
        }
        return "create order success";
    }
}
