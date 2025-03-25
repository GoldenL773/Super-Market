package org.example.spring.supermarket.service.impl;

import org.example.spring.supermarket.DTO.ProductDTO;
import org.example.spring.supermarket.entity.Order;
import org.example.spring.supermarket.entity.OrderDetails;
import org.example.spring.supermarket.repository.OrderDetailRepository;
import org.example.spring.supermarket.service.CustomerService;
import org.example.spring.supermarket.service.OrderDetailService;
import org.example.spring.supermarket.service.OrderService;
import org.example.spring.supermarket.service.ProductService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class OrderDetailServiceImpl implements OrderDetailService {
    private OrderDetailRepository orderDetailRepository;
    private CustomerService customerService;
    private OrderService orderService;
    private ProductService productService;

    public OrderDetailServiceImpl(OrderDetailRepository orderDetailRepository, CustomerService customerService, OrderService orderService, ProductService productService) {
        this.orderDetailRepository = orderDetailRepository;
        this.customerService = customerService;
        this.orderService = orderService;
        this.productService = productService;
    }


    @Override
    public List<OrderDetails> getOrderDetailsByOrder(Order order) {
        return orderDetailRepository.findByOrder(order);
    }

    @Override
    public Map<ProductDTO, Integer> getOrderDetailsMapByOrder(Order order) {
        List<OrderDetails> orderDetails = getOrderDetailsByOrder(order);
        Map<ProductDTO, Integer> orderDetailsMap = new HashMap<>();
        for (OrderDetails detail : orderDetails) {
            ProductDTO productDTO = productService.getById(detail.getProduct().getId());
            orderDetailsMap.put(productDTO, detail.getQuantity());
        }
        return orderDetailsMap;
    }
}
