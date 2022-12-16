package com.example.clothingstore.config;

import com.example.clothingstore.model.*;
import com.example.clothingstore.security.principal.UserDetailService;
import com.example.clothingstore.service.impl.*;
import org.springframework.web.bind.annotation.RequestBody;

import java.sql.Timestamp;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class OrderCreator {
    public static OrderEntity createOrder(UserDetailService userDetailService, ProductServiceImpl productService, TypeServiceImpl typeService, AddressServiceImpl addressService, TransactionServiceImpl orderDetailService, OrderServiceImpl orderService, CartProductServiceImpl cartProductService, List<Object> req, String ordStatus) throws ParseException {
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
                return null;
            }
//            // update type entity
//            typeEntity.setSold(typeEntity.getSold() + quantity);
//            typeEntity.setQuantity(typeEntity.getQuantity() - quantity);

            totalcost += unitPrice*quantity;

            // check if type disabled -> bad request
            if (typeEntity.getStatus().equals(LocalVariable.disableStatus)) {
                return null;
            }

            transactionEntities.add(transactionEntity);
        }
        // map value for orderEntity and check if user want to add new address
        if (orderInformation.get("address") != null) {
            orderEntity = new OrderEntity(totalcost, orderInformation.get("note") == null ? "" : orderInformation.get("note"), Long.parseLong(orderInformation.get("shipping_fee") == null ? "25000" : orderInformation.get("shipping_fee")), orderInformation.get("payment") == null ? "VNPAY" : orderInformation.get("payment"), ordStatus , user.getFullname() ,user.getAddress(), user.getPhone());
        } else
        {
            AddressEntity address = addressService.getAddressDefaultOfUser(user.getId());
            orderEntity = new OrderEntity(totalcost, orderInformation.get("note") == null ? address.getNote() : orderInformation.get("note"), Long.valueOf(25000) ,ordStatus , "VNPay", address.getName(), address.getAddress(), address.getPhoneNumber());
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
        // delete cartProd uct from DB
        for (TransactionEntity transactionEntity: transactionEntities)
        {
            transactionEntity.setCreate_at(new Timestamp(System.currentTimeMillis()));
            transactionEntity.setUpdate_at(new Timestamp(System.currentTimeMillis()));
            orderDetailService.save(transactionEntity);
            cartProductService.deleteProductInCart(user.getId(), transactionEntity.getProductEntity().getId(), transactionEntity.getColor(), transactionEntity.getSize());
        }
        return orderEntity;
    }
}
