package com.wilnan.order_service.controller;

import com.wilnan.order_service.dto.ApiResponse;
import com.wilnan.order_service.dto.CreateOrderRequest;
import com.wilnan.order_service.dto.OrderResponse;
import com.wilnan.order_service.dto.UpdateOrderRequest;
import com.wilnan.order_service.service.OrderService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/orders")
@RequiredArgsConstructor
public class OrderController {

    private final OrderService orderService;

    @PostMapping
    public ResponseEntity<ApiResponse<OrderResponse>> createOrder(
            @Valid @RequestBody CreateOrderRequest request
    ) {
        OrderResponse order = orderService.createOrder(request);

        ApiResponse<OrderResponse> response = ApiResponse.<OrderResponse>builder()
                .success(true)
                .message("Order created successfully")
                .data(order)
                .build();

        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> getOrderById(@PathVariable Long id) {
        OrderResponse order = orderService.getOrderById(id);

        ApiResponse<OrderResponse> response = ApiResponse.<OrderResponse>builder()
                .success(true)
                .message("Order retrieved successfully")
                .data(order)
                .build();

        return ResponseEntity.ok(response);
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<OrderResponse>>> getAllOrders() {
        List<OrderResponse> orders = orderService.getAllOrders();

        ApiResponse<List<OrderResponse>> response = ApiResponse.<List<OrderResponse>>builder()
                .success(true)
                .message("Orders retrieved successfully")
                .data(orders)
                .build();

        return ResponseEntity.ok(response);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ApiResponse<OrderResponse>> updateOrder(
            @PathVariable Long id,
            @Valid @RequestBody UpdateOrderRequest request
    ) {
        OrderResponse updatedOrder = orderService.updateOrder(id, request);

        ApiResponse<OrderResponse> response =
                ApiResponse.<OrderResponse>builder()
                        .success(true)
                        .message("Order updated successfully")
                        .data(updatedOrder)
                        .build();

        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<ApiResponse<Object>> deleteOrder(
            @PathVariable Long id
    ) {
        orderService.deleteOrder(id);

        ApiResponse<Object> response = ApiResponse.builder()
                .success(true)
                .message("Order deleted successfully")
                .data(null)
                .build();

        return ResponseEntity.ok(response);
    }
}