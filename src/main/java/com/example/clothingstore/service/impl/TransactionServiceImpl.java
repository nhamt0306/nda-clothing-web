package com.example.clothingstore.service.impl;

import com.example.clothingstore.model.TransactionEntity;
import com.example.clothingstore.repository.TransactionRepository;
import com.example.clothingstore.service.TransactionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class TransactionServiceImpl implements TransactionService {
    @Autowired
    TransactionRepository transactionRepository;
    @Override
    public TransactionEntity save(TransactionEntity transactionEntity) {
        return transactionRepository.save(transactionEntity);
    }

    @Override
    public List<TransactionEntity> getAllByOrderId(Long orderId) {
        return transactionRepository.findAllByOrderEntityId(orderId);
    }
}
