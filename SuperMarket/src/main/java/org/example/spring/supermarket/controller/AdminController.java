package org.example.spring.supermarket.controller;

import org.example.spring.supermarket.DTO.CategoryDTO;
import org.example.spring.supermarket.entity.*;
import org.example.spring.supermarket.service.*;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.print.Pageable;
import java.util.List;

@Controller
@RequestMapping("/admin")
public class AdminController {

    @Autowired
    private ProductService productService;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private OrderService orderService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private InventoryService inventoryService;

    @Autowired
    private StaffService staffService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private PromotionService promotionService;

    // Language: java
    @GetMapping("/dashboard")
    public String dashboard(Model model) {
        // Retrieve statistics from services
        int categoryCount = categoryService.getAll().size();
        int orderCount = orderService.findAll().size();
        int customerCount = customerService.getAllCustomers().size();

        // Add statistics to the model
        model.addAttribute("categoryCount", categoryCount);
        model.addAttribute("orderCount", orderCount);
        model.addAttribute("customerCount", customerCount);

        return "admin/dashboard";
    }


    // Categories Management
    @GetMapping("/categories")
    public String listCategories(Model model) {
        model.addAttribute("categories", categoryService.getAll());
        return "admin/categories/list";
    }

    @GetMapping("/categories/new")
    public String newCategoryForm(Model model) {
        model.addAttribute("category", new Categories());
        return "admin/categories/form";
    }

    @PostMapping("/categories")
    public String saveCategory(@ModelAttribute CategoryDTO categoryDTO, RedirectAttributes redirectAttributes) {
        categoryService.save(categoryDTO);
        redirectAttributes.addFlashAttribute("message", "Category saved successfully!");
        return "redirect:/admin/categories";
    }

    // Orders Management
    @GetMapping("/orders")
    public String listOrders(Model model) {
        model.addAttribute("orders", orderService.findAll());
        return "admin/orders/list";
    }

    @GetMapping("/orders/{id}")
    public String viewOrder(@PathVariable int id, Model model) {
        Order order = orderService.getOrderById(id)
                .orElseThrow(() -> new RuntimeException("Order not found with id: " + id));
        model.addAttribute("order", order);
        model.addAttribute("orderDetails", orderService.getOrderDetails(id));
        return "admin/orders/details";
    }

    @PostMapping("/orders/{id}/status")
    public String updateOrderStatus(@PathVariable int id, @RequestParam String status, RedirectAttributes redirectAttributes) {
        orderService.updateStatus(id, status);
        redirectAttributes.addFlashAttribute("message", "Order status updated successfully!");
        return "redirect:/admin/orders/" + id;
    }

    // Customers Management
    @GetMapping("/customers")
    public String listCustomers(Pageable pageable, Model model) {
        Page<Customer> customers = customerService.getAllCustomers((org.springframework.data.domain.Pageable) pageable);
        model.addAttribute("customers", customers);
        return "admin/customers/list";
    }

    // Language: java
    @GetMapping("/customers/{id}")
    public String viewCustomer(@PathVariable int id, Model model) {
        Customer customer = customerService.getCustomerById(id)
                .orElseThrow(() -> new RuntimeException("Customer not found with id: " + id));
        model.addAttribute("customer", customer);
        model.addAttribute("orders", orderService.getOrdersByCustomer(customer));
        return "admin/customers/details";
    }

    // Inventory Management
    @GetMapping("/inventory")
    public String listInventory(Model model) {
         model.addAttribute("inventory", inventoryService.findAll());
        return "admin/inventory/list";
    }

    @GetMapping("/inventory/new")
    public String newInventoryForm(Model model) {
        model.addAttribute("stockEntry", new StockEntry());
        // model.addAttribute("products", productService.findAll());
        // model.addAttribute("suppliers", supplierService.findAll());
        return "admin/inventory/form";
    }

    @PostMapping("/inventory")
    public String saveInventory(@ModelAttribute StockEntry stockEntry, RedirectAttributes redirectAttributes) {
//         inventoryService.save(stockEntry);
        redirectAttributes.addFlashAttribute("message", "Inventory updated successfully!");
        return "redirect:/admin/inventory";
    }


    // Promotions Management
    @GetMapping("/promotions")
    public String listPromotions(Model model) {
        // model.addAttribute("promotions", promotionService.findAll());
        return "admin/promotions/list";
    }

    @GetMapping("/promotions/new")
    public String newPromotionForm(Model model) {
        model.addAttribute("promotion", new Promotion());
        // model.addAttribute("products", productService.findAll());
        return "admin/promotions/form";
    }

    @PostMapping("/promotions")
    public String savePromotion(@ModelAttribute Promotion promotion, RedirectAttributes redirectAttributes) {
        // promotionService.save(promotion);
        redirectAttributes.addFlashAttribute("message", "Promotion saved successfully!");
        return "redirect:/admin/promotions";
    }

    // Reports
    @GetMapping("/reports/sales")
    public String salesReport(Model model) {
//        model.addAttribute("salesData", orderService.getSalesReportData());
        return "admin/reports/sales";
    }

    @GetMapping("/reports/inventory")
    public String inventoryReport(Model model) {
        // model.addAttribute("inventoryData", inventoryService.getInventoryReportData());
        return "admin/reports/inventory";
    }
}