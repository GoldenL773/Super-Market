package org.example.spring.supermarket.controller;



import jakarta.servlet.http.HttpSession;
import org.example.spring.supermarket.dto.ProductDTO;
import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.entity.Order;
import org.example.spring.supermarket.service.CartService;
import org.example.spring.supermarket.service.CategoryService;
import org.example.spring.supermarket.service.ProductService;

import org.example.spring.supermarket.service.InventoryService;
import org.springframework.stereotype.Controller;

import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.Map;
import java.util.Optional;


@Controller
@RequestMapping("/cart")
public class CartController {
    private CategoryService categoryService;
    private CartService cartService;
    private ProductService productService;
    private InventoryService inventoryService;
    public CartController(CategoryService categoryService, CartService cartService, ProductService productService, InventoryService inventoryService) {
        this.categoryService = categoryService;
        this.cartService = cartService;
        this.productService = productService;
        this.inventoryService = inventoryService;
    }
    @GetMapping
    public String viewCart(Model model, HttpSession session) {
        String sessionId = session.getId();
        Map<ProductDTO, Integer> cartItems = cartService.getCartItems(sessionId);
        double total = cartService.getCartTotal(sessionId);

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);

        return "cart";
    }
    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") int productId,
                            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        if (quantity <= 0) {
            redirectAttributes.addFlashAttribute("error", "Quantity must be greater than 0");
            return "redirect:/shop";
        }

        String sessionId = session.getId();
        productService.getProductById(productId).ifPresent(product -> {
            // Check if enough stock is available
            if (inventoryService.getInventory(productId).getQuantity() >= quantity) {
                cartService.addToCart(sessionId, productId, quantity);
                redirectAttributes.addFlashAttribute("success", "Product added to cart successfully");
            } else {
                redirectAttributes.addFlashAttribute("error", "Not enough stock available");
            }
        });

        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCartItem(@RequestParam("productId") int productId,
                                 @RequestParam("quantity") int quantity,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        if (quantity < 0) {
            redirectAttributes.addFlashAttribute("error", "Quantity cannot be negative");
            return "redirect:/cart";
        }

        String sessionId = session.getId();
        if (quantity == 0) {
            cartService.removeFromCart(sessionId, productId);
            redirectAttributes.addFlashAttribute("success", "Item removed from cart");
        } else {
            Optional<ProductDTO> productOptional = productService.getProductById(productId);
            if (productOptional.isPresent()) {
                ProductDTO product = productOptional.get();
                // Check if enough stock is available
                if (inventoryService.getInventory(productId).getQuantity()  >= quantity) {
                    cartService.updateCartItemQuantity(sessionId, productId, quantity);
                    redirectAttributes.addFlashAttribute("success", "Cart updated successfully");
                } else {
                    redirectAttributes.addFlashAttribute("error", "Not enough stock available");
                }
            } else {
                redirectAttributes.addFlashAttribute("error", "Product not found");
            }
        }

        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("productId") int productId,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        String sessionId = session.getId();
        cartService.removeFromCart(sessionId, productId);
        redirectAttributes.addFlashAttribute("success", "Item removed from cart");

        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(HttpSession session, RedirectAttributes redirectAttributes) {
        cartService.clearCart(session.getId());
        redirectAttributes.addFlashAttribute("success", "Cart cleared successfully");
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkoutForm(Model model, HttpSession session) {
        String sessionId = session.getId();
        Map<ProductDTO, Integer> cartItems = cartService.getCartItems(sessionId);
        double total = cartService.getCartTotal(sessionId);

        if (cartItems.isEmpty()) {
            model.addAttribute("error", "Your cart is empty");
            return "redirect:/cart";
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);

        // Get the currently authenticated customer


        return "redirect:/cart/checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(HttpSession session, RedirectAttributes redirectAttributes) {
        String sessionId = session.getId();
        Customer customer = (Customer) session.getAttribute("customer");
        Order order = cartService.checkout(sessionId, customer);

        if (order == null) {
            redirectAttributes.addFlashAttribute("error", "Your cart is empty");
            return "redirect:/cart";
        }

        redirectAttributes.addFlashAttribute("success", "Order placed successfully");
        return "redirect:/orders/confirmation/" + order.getId();
    }
}
