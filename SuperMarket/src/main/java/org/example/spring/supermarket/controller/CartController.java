package org.example.spring.supermarket.controller;
import jakarta.servlet.http.HttpSession;
import org.example.spring.supermarket.dto.ProductDTO;
import org.example.spring.supermarket.entity.Cart;
import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.entity.Order;
import org.example.spring.supermarket.entity.Product;
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

import java.util.List;
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
        Customer customer = (Customer) session.getAttribute("customer");
        Map<ProductDTO, Integer> cartItems = cartService.getCartItems(customer);
        double total = cartService.getCartTotal(customer);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);

        return "cart";
    }
    @PostMapping("/add")
    public String addToCart(@RequestParam("productId") int productId,
                            @RequestParam(value = "quantity", defaultValue = "1") int quantity,
                            HttpSession session,
                            RedirectAttributes redirectAttributes) {

        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        if (quantity <= 0) {
            redirectAttributes.addFlashAttribute("error", "Quantity must be greater than 0");
            return "redirect:/shop";
        }
        Product product = new Product();
        product.setId(productId);
        productService.getProductById(productId).ifPresent(productDTO -> {
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setImage(productDTO.getImage());
            product.setCategory(categoryService.getCategories(productDTO.getCategoryId()));
            product.setDescription(productDTO.getDescription());
            product.setCreatedAt(productDTO.getCreatedAt());
        });
        if (inventoryService.getInventory(productId).getQuantity() >= quantity) {
            cartService.addToCart(customer, product, quantity);
            redirectAttributes.addFlashAttribute("success", "Product added to cart successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Not enough stock available");
        }
        return "redirect:/cart";
    }

    @PostMapping("/update")
    public String updateCartItem(@RequestParam("productId") int productId,
                                 @RequestParam("quantity") int quantity,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        if (quantity < 0) {
            redirectAttributes.addFlashAttribute("error", "Quantity cannot be negative");
            return "redirect:/cart";
        }
        Product product = new Product();
        product.setId(productId);
        productService.getProductById(productId).ifPresent(productDTO -> {
            product.setName(productDTO.getName());
            product.setPrice(productDTO.getPrice());
            product.setImage(productDTO.getImage());
            product.setCategory(categoryService.getCategories(productDTO.getCategoryId()));
            product.setDescription(productDTO.getDescription());
            product.setCreatedAt(productDTO.getCreatedAt());
        });
        if (inventoryService.getInventory(productId).getQuantity() >= quantity) {
            cartService.updateCartItemQuantity(customer, product, quantity);
            redirectAttributes.addFlashAttribute("success", "Cart updated successfully");
        } else {
            redirectAttributes.addFlashAttribute("error", "Not enough stock available");
        }
        return "redirect:/cart";
    }

    @PostMapping("/remove")
    public String removeFromCart(@RequestParam("productId") int productId,
                                 HttpSession session,
                                 RedirectAttributes redirectAttributes) {

        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        Product product = new Product();
        product.setId(productId);
        cartService.removeFromCart(customer, product);
        redirectAttributes.addFlashAttribute("success", "Item removed from cart");
        return "redirect:/cart";
    }

    @PostMapping("/clear")
    public String clearCart(HttpSession session, RedirectAttributes redirectAttributes) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        cartService.clearCart(customer);
        redirectAttributes.addFlashAttribute("success", "Cart cleared successfully");
        return "redirect:/cart";
    }

    @GetMapping("/checkout")
    public String checkoutForm(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        Map<ProductDTO, Integer> cartItems = cartService.getCartItems(customer);
        double total = cartService.getCartTotal(customer);

        if (cartItems.isEmpty()) {
            model.addAttribute("error", "Your cart is empty");
            return "redirect:/cart";
        }

        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);

        return "checkout";
    }

    @PostMapping("/checkout")
    public String processCheckout(HttpSession session, RedirectAttributes redirectAttributes) {
        Customer customer = (Customer) session.getAttribute("customer");
        if (customer == null) {
            return "redirect:/login";
        }
        Order order = cartService.checkout(customer);

        if (order == null) {
            redirectAttributes.addFlashAttribute("error", "Your cart is empty");
            return "redirect:/cart";
        }

        redirectAttributes.addFlashAttribute("success", "Order placed successfully");
        return "redirect:/cart";
    }
}
