package com.wilnan.order_service.service;

import com.wilnan.order_service.dto.CreateOrderRequest;
import com.wilnan.order_service.dto.OrderResponse;
import com.wilnan.order_service.dto.UpdateOrderRequest;

import java.util.List;

public interface OrderService {

    OrderResponse createOrder(CreateOrderRequest request);

    OrderResponse getOrderById(Long id);

    List<OrderResponse> getAllOrders();
    OrderResponse updateOrder(Long id, UpdateOrderRequest request);

    void deleteOrder(Long id);
}
