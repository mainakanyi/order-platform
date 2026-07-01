package com.wilnan.order_service.service;

import com.wilnan.order_service.dto.CreateOrderRequest;
import com.wilnan.order_service.dto.OrderResponse;
import com.wilnan.order_service.dto.UpdateOrderRequest;
import com.wilnan.order_service.enums.OrderStatus;
import com.wilnan.order_service.exception.ResourceNotFoundException;
import com.wilnan.order_service.model.Order;
import com.wilnan.order_service.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import com.wilnan.order_service.event.OrderCreatedEvent;
import com.wilnan.order_service.producer.OrderEventProducer;

import java.time.LocalDateTime;

import java.util.List;

@Service
@RequiredArgsConstructor
public class OrderServiceImpl implements OrderService {

    private final OrderRepository orderRepository;
    private final OrderEventProducer orderEventProducer;

    @Override
    public OrderResponse createOrder(CreateOrderRequest request) {
        Order order = Order.builder()
                .customerName(request.getCustomerName())
                .item(request.getItem())
                .amount(request.getAmount())
                .status(OrderStatus.CREATED)
                .build();

        Order savedOrder = orderRepository.save(order);

        OrderCreatedEvent event = OrderCreatedEvent.builder()
                .orderId(savedOrder.getId())
                .customerName(savedOrder.getCustomerName())
                .item(savedOrder.getItem())
                .amount(savedOrder.getAmount())
                .status(savedOrder.getStatus())
                .createdAt(LocalDateTime.now())
                .build();

        orderEventProducer.publishOrderCreatedEvent(event);

        return mapToResponse(savedOrder);
    }

    @Override
    public OrderResponse getOrderById(Long id) {
        Order order = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + id
                        )
                );

        return mapToResponse(order);
    }

    @Override
    public List<OrderResponse> getAllOrders() {
        return orderRepository.findAll()
                .stream()
                .map(this::mapToResponse)
                .toList();
    }

    @Override
    public OrderResponse updateOrder(Long id, UpdateOrderRequest request) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + id
                        )
                );

        existingOrder.setCustomerName(request.getCustomerName());
        existingOrder.setItem(request.getItem());
        existingOrder.setAmount(request.getAmount());
        existingOrder.setStatus(request.getStatus());

        Order updatedOrder = orderRepository.save(existingOrder);

        return mapToResponse(updatedOrder);
    }

    @Override
    public void deleteOrder(Long id) {
        Order existingOrder = orderRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Order not found with id: " + id
                        )
                );

        orderRepository.delete(existingOrder);
    }

    private OrderResponse mapToResponse(Order order) {
        return OrderResponse.builder()
                .id(order.getId())
                .customerName(order.getCustomerName())
                .item(order.getItem())
                .amount(order.getAmount())
                .status(order.getStatus())
                .build();
    }

}