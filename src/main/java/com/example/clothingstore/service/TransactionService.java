package com.example.clothingstore.service;

import com.example.clothingstore.model.TransactionEntity;

import java.util.List;

public interface TransactionService {
    TransactionEntity save(TransactionEntity transactionEntity);
    List<TransactionEntity> getAllByOrderId(Long orderId);
}
