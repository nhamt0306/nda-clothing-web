package com.example.clothingstore.service;

import com.example.clothingstore.model.OrderEntity;
import org.springframework.data.domain.Page;

import java.util.List;

public interface OrderService {
    Page<OrderEntity> getAllPaging(int page);

    Page<OrderEntity> getAllByUserId(Long UserId,int page);

    List<OrderEntity> getAllPendingOrderByUserId(Long UserId, String Status);

    List<OrderEntity> getAllHistoryOrderByUserId(Long UserId, String Status);

    OrderEntity findOrderById(Long OrderId);
    void addNewOrder(OrderEntity orderEntity);
    List<OrderEntity> getAllOrderByUserId(Long userid);
    List<OrderEntity> getAllByUserId(Long userid);
    List<OrderEntity> getAllOrder();
    boolean existOrderByUser(Long userId);
}
