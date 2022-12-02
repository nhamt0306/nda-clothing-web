package com.example.clothingstore.controller;

import com.example.clothingstore.config.LocalVariable;
import com.example.clothingstore.mapper.*;
import com.example.clothingstore.model.*;
import com.example.clothingstore.security.principal.UserDetailService;
import com.example.clothingstore.service.impl.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@RestController
public class OrderController {
    @Autowired
    UserDetailService userDetailService;
    @Autowired
    OrderServiceImpl orderService;
    @Autowired
    CartProductServiceImpl cartProductService;
    @Autowired
    ProductServiceImpl productService;
    @Autowired
    TransactionServiceImpl orderDetailService;
    @Autowired
    AddressServiceImpl addressService;
    @Autowired
    TypeServiceImpl typeService;

    @PostMapping("/user/order/create")
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
            if (typeEntity == null)
            {
                return new ResponseEntity<>("Type of product is not exist!", HttpStatus.BAD_REQUEST);
            }
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

    @GetMapping("/admin/orders/getOrderByUserId/{id}")
    public ResponseEntity<?> getOrderByUserId(@PathVariable long id){
        try {
            List<OrderEntity> orderEntityList = orderService.getAllOrderByUserId(id);
            List<OrderMapper> orderMappers = new ArrayList<>();
            for (OrderEntity orderEntity : orderEntityList)
            {
                OrderMapper orderMapper = new OrderMapper(orderEntity.getId(), orderEntity.getTotalPrice(), orderEntity.getNote(), orderEntity.getShippingFee(), orderEntity.getPayment(), orderEntity.getStatus(), orderEntity.getAddress(), orderEntity.getPhone(), orderEntity.getCreate_at());
                orderMapper.setOrdName(orderEntity.getName());
                // get transaction of ~ order
                List<TransactionMapper> transactionMappers = new ArrayList<>();
                for(TransactionEntity transactionEntity : orderDetailService.getAllByOrderId(orderEntity.getId()))
                {
                    TransactionMapper transactionMapper = new TransactionMapper(transactionEntity.getId(), transactionEntity.getUnitPrice(), transactionEntity.getQuantity(), transactionEntity.getProductEntity().getId(), transactionEntity.getProductEntity().getImage(), transactionEntity.getProductEntity().getName(), transactionEntity.getColor(), transactionEntity.getSize());
                    transactionMappers.add(transactionMapper);
                }
                orderMapper.setTransactionMapper(transactionMappers);
                orderMappers.add(orderMapper);
            }

            return ResponseEntity.ok(orderMappers);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(LocalVariable.messageCannotFindCat + id, HttpStatus.NOT_FOUND);
        }
    }

    @GetMapping("/user/order/getAll")
    public ResponseEntity<?> getOrderByUserLogin(){
        try {
            return getOrderByUserId(userDetailService.getCurrentUser().getId());
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(LocalVariable.messageCannotFindCat , HttpStatus.NOT_FOUND);
        }
    }

    // category paging
    @GetMapping("/admin/order")
    public Object getAllOrder(@RequestParam(defaultValue = "1") Integer pageNo,
                                 @RequestParam(defaultValue = "100") Integer pageSize,
                                 @RequestParam(defaultValue = "id") String sortBy) {
        Integer maxPageSize;
        Integer maxPageNo;
        List<OrderEntity> orderEntityList = new ArrayList<>();

        maxPageSize = orderService.getAllOrder().size();
        if (pageSize > maxPageSize)
        {
            pageSize = 12;
        }
        maxPageNo = maxPageSize / pageSize;
        if (pageNo > maxPageNo +1)
        {
            pageNo = maxPageNo +1;
        }
        orderEntityList = orderService.getAllPaging(pageNo-1, pageSize, sortBy, "Active");

        List<OrderMapper> orderMappers = new ArrayList<>();
        for (OrderEntity orderEntity : orderEntityList)
        {
            OrderMapper orderMapper = new OrderMapper(orderEntity.getId(), orderEntity.getTotalPrice(), orderEntity.getNote(), orderEntity.getShippingFee(), orderEntity.getPayment(), orderEntity.getStatus(), orderEntity.getAddress(), orderEntity.getPhone(), orderEntity.getCreate_at());
            orderMapper.setOrdName(orderEntity.getName());
            // get transaction of ~ order
            List<TransactionMapper> transactionMappers = new ArrayList<>();
            for(TransactionEntity transactionEntity : orderDetailService.getAllByOrderId(orderEntity.getId()))
            {
                TransactionMapper transactionMapper = new TransactionMapper(transactionEntity.getId(), transactionEntity.getUnitPrice(), transactionEntity.getQuantity(), transactionEntity.getProductEntity().getId(), transactionEntity.getProductEntity().getImage(), transactionEntity.getProductEntity().getName(), transactionEntity.getColor(), transactionEntity.getSize());
                transactionMappers.add(transactionMapper);
            }
            orderMapper.setTransactionMapper(transactionMappers);
            orderMappers.add(orderMapper);
        }

        OrderPagingResponse orderPagingResponse = new OrderPagingResponse(orderMappers, maxPageSize);
        return orderPagingResponse;
    }


    @GetMapping("/admin/orders/getAll")
    public ResponseEntity<?> getOrderByAdmin(){
        try {
            List<OrderEntity> orderEntityList = orderService.getAllOrder();
            List<OrderMapper> orderMappers = new ArrayList<>();
            for (OrderEntity orderEntity : orderEntityList)
            {
                OrderMapper orderMapper = new OrderMapper(orderEntity.getId(), orderEntity.getTotalPrice(),
                        orderEntity.getNote(), orderEntity.getShippingFee(), orderEntity.getPayment(), orderEntity.getStatus(),
                        orderEntity.getAddress(), orderEntity.getPhone(), orderEntity.getCreate_at(), orderEntity.getName());
                // get transaction of ~ order
                List<TransactionMapper> transactionMappers = new ArrayList<>();
                for(TransactionEntity transactionEntity : orderDetailService.getAllByOrderId(orderEntity.getId()))
                {
                    TransactionMapper transactionMapper = new TransactionMapper(transactionEntity.getId(), transactionEntity.getUnitPrice(), transactionEntity.getQuantity(), transactionEntity.getProductEntity().getId(), transactionEntity.getProductEntity().getImage(), transactionEntity.getProductEntity().getName(), transactionEntity.getColor(), transactionEntity.getSize());
                    transactionMappers.add(transactionMapper);
                }
                orderMapper.setTransactionMapper(transactionMappers);
                orderMappers.add(orderMapper);
            }

            return ResponseEntity.ok(orderMappers);
        }
        catch (Exception e)
        {
            return new ResponseEntity<>(LocalVariable.messageCannotFindCat , HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/admin/order/deny/{id}")
    public Object cancelOrder(@PathVariable long id) {
        OrderEntity orderEntity = orderService.findOrderById(id);
        if (orderEntity.getStatus().equals(LocalVariable.pendingMessage)) // Nếu tình trạng là đang đợi thì mới được hủy
        {
            orderEntity.setStatus(LocalVariable.cancelMessage);
            orderEntity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
            orderService.addNewOrder(orderEntity);
            return "cancel order success";
        }
        return "cancel order fail";
    }

    @PostMapping("/user/order/deny/{id}")
    public Object denyOrderByUser(@PathVariable long id) {
        if (!orderService.existOrderByUser(userDetailService.getCurrentUser().getId()))
        {
            return "This order is not yours!";
        }
        OrderEntity orderEntity = orderService.findOrderById(id);
        if (orderEntity.getStatus().equals(LocalVariable.pendingMessage)) // Nếu tình trạng là đang đợi thì mới được hủy
        {
            orderEntity.setStatus(LocalVariable.cancelMessage);
            orderEntity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
            orderService.addNewOrder(orderEntity);
            return "cancel order success";
        }
        return "cancel order fail";
    }

    @PostMapping("/admin/order/accept/{id}")
    public Object acceptOrder(@PathVariable long id) {
        OrderEntity orderEntity = orderService.findOrderById(id);
        if (orderEntity.getStatus().equals(LocalVariable.deliveringMessage)) // Nếu tình trạng là đang đợi thì mới được hủy
        {
            orderEntity.setStatus(LocalVariable.doneMessage);
            orderEntity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
            orderService.addNewOrder(orderEntity);
            return "update status order with done: success";
        }
        //update product quantity
        List<TransactionEntity> transactionEntities = orderDetailService.getAllByOrderId(orderEntity.getId());
        for (TransactionEntity transactionEntity : transactionEntities)
        {
            TypeEntity typeEntity = typeService.getTypeByColorAndSizeAndProductId(transactionEntity.getColor(), transactionEntity.getSize(), transactionEntity.getProductEntity().getId());
            if (transactionEntity.getQuantity() > typeEntity.getQuantity())
            {
                return "Dont enought quantity of "+ transactionEntity.getProductEntity().getName();
            }
            typeEntity.setSold(typeEntity.getSold() + transactionEntity.getQuantity());
            typeEntity.setQuantity(typeEntity.getQuantity() - transactionEntity.getQuantity());
            typeService.save(typeEntity);
        }

        if (orderEntity.getStatus().equals(LocalVariable.pendingMessage)) // Nếu tình trạng là đang đợi thì mới được hủy
        {
            orderEntity.setStatus(LocalVariable.deliveringMessage);
            orderEntity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
            orderService.addNewOrder(orderEntity);
            return "update status order with done: success";
        }

        return "update status order with done: fail";
    }

    //url return payment vnpay
    @GetMapping("/order/notification")
    public Object returnResultPayment(HttpServletRequest request){
        if (request.getParameter("vnp_ResponseCode").equals("24"))
        {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Payment failed!");
        }
        return acceptOrder(Long.valueOf(request.getParameter("vnp_TxnRef")));
    }

    @GetMapping("/get_deliver_fee")
    public Object getShippingFee(@RequestParam String f, @RequestParam String t, @RequestParam String w) {
        String httpRequest = "Can't get deliver fee, Server Busy";

        try {
            URL url = new URL("http://www.vnpost.vn/vi-vn/tra-cuu-gia-cuoc?from=" + f + "&to=" + t + "&weight=" + w);
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");
            String line;
            InputStreamReader streamReader = new InputStreamReader(connection.getInputStream(), StandardCharsets.UTF_8);
            BufferedReader bufferedReader = new BufferedReader(streamReader);
            StringBuilder resHtml = new StringBuilder();
            while ((line = bufferedReader.readLine()) != null) {
                resHtml.append(line);
            }
            bufferedReader.close();
            httpRequest = resHtml.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return httpRequest;
    }
}
