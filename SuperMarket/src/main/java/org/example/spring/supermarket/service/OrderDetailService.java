package org.example.spring.supermarket.service;

import org.example.spring.supermarket.DTO.ProductDTO;
import org.example.spring.supermarket.entity.Order;
import org.example.spring.supermarket.entity.OrderDetails;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface OrderDetailService {
    List<OrderDetails> getOrderDetailsByOrder(Order order);
    Map<ProductDTO, Integer> getOrderDetailsMapByOrder(Order order);
}
