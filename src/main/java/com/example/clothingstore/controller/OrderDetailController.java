package com.example.clothingstore.controller;

import com.example.clothingstore.config.mapper.TransactionMapper;
import com.example.clothingstore.model.TransactionEntity;
import com.example.clothingstore.service.impl.ProductServiceImpl;
import com.example.clothingstore.service.impl.TransactionServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
import java.util.List;

@RestController
public class OrderDetailController {
    @Autowired
    TransactionServiceImpl orderDetailService;
    @Autowired
    ProductServiceImpl productService;

    @GetMapping("/user/orderdetail/{id}")
    public ResponseEntity<?> getAllByOrderId(@PathVariable long id)
    {
        List<TransactionMapper> transactionMappers = new ArrayList<>();
        for(TransactionEntity transactionEntity : orderDetailService.getAllByOrderId(id))
        {
            TransactionMapper transactionMapper = new TransactionMapper(transactionEntity.getId(), transactionEntity.getUnitPrice(), transactionEntity.getQuantity(), transactionEntity.getProductEntity().getId(), transactionEntity.getProductEntity().getImage(), transactionEntity.getProductEntity().getName(), transactionEntity.getColor(), transactionEntity.getSize());
            transactionMappers.add(transactionMapper);
        }
        return ResponseEntity.ok(transactionMappers);
    }
}
