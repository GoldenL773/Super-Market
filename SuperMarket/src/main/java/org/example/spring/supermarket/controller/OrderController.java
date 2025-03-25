package org.example.spring.supermarket.controller;

import jakarta.servlet.http.HttpSession;
import org.example.spring.supermarket.DTO.ProductDTO;
import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.entity.Order;
import org.example.spring.supermarket.service.CartService;
import org.example.spring.supermarket.service.OrderDetailService;
import org.example.spring.supermarket.service.OrderService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
@Controller
@RequestMapping("/transaction")
public class OrderController {
    private final OrderService orderService;
    private final CartService cartService;
    private final OrderDetailService orderDetailService;
    private static final Logger logger = LoggerFactory.getLogger(OrderController.class);

    public OrderController(OrderService orderService, CartService cartService, OrderDetailService orderDetailService) {
        this.orderService = orderService;
        this.cartService = cartService;
        this.orderDetailService = orderDetailService;
    }

    @GetMapping()
    public String showTransaction(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        List<Order> orders = orderService.getOrdersByCustomer(customer);
        Map<Order, Map<ProductDTO, Integer>> orderDetailsMap = new HashMap<>();
        for (Order order : orders) {
            orderDetailsMap.put(order, orderDetailService.getOrderDetailsMapByOrder(order));
        }
        Map<ProductDTO, Integer> cartItems = cartService.getCartItems(customer);
        double total = cartService.getCartTotal(customer);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        model.addAttribute("orders", orders);
        model.addAttribute("orderDetailsMap", orderDetailsMap);
        logger.warn("Debug Order:" + orders.size());
        return "transaction";
    }
}
