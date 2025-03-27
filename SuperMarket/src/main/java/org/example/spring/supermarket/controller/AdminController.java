package org.example.spring.supermarket.controller;

import jakarta.servlet.http.HttpSession;
import org.example.spring.supermarket.DTO.CategoryDTO;
import org.example.spring.supermarket.entity.*;
import org.example.spring.supermarket.service.*;
import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.data.domain.Page;
//import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.print.Pageable;
import java.math.BigDecimal;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;

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
    private OrderDetailService orderDetailService;

    @Autowired
    private SupplierService supplierService;

    @Autowired
    private PromotionService promotionService;

    // Language: java
    @GetMapping("/dashboard")
    public String dashboard(Model model, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        Optional<Staff> staffOpt = staffService.getStaffByUsername(user.getUsername());

        if (staffOpt.isPresent()) {
            Staff staff = staffOpt.get();
            session.setAttribute("staff", staff);
            model.addAttribute("staff", staff);
        } else {
            return "redirect:/admin/login";
        }

        // Thống kê tổng số
        int categoryCount = categoryService.getAll().size();
        int orderCount = orderService.findAll().size();
        int customerCount = customerService.getAllCustomers().size();
        model.addAttribute("categoryCount", categoryCount);
        model.addAttribute("orderCount", orderCount);
        model.addAttribute("customerCount", customerCount);

        // Thống kê thu nhập theo tháng (trừ Pending & Cancelled)
        Map<String, BigDecimal> monthlyIncome = orderService.getMonthlyIncome();
        model.addAttribute("monthlyIncome", monthlyIncome);

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



    @GetMapping("/customers")
    public String listCustomers(Model model) {
        List<Customer> customers = customerService.getAllCustomers();
        model.addAttribute("customers", customers);
        return "admin/customers"; // Name of the template that will show customer list
    }

    // Language: java
    @GetMapping("/customers/{id}")
    public String customerDetail(@PathVariable int id, Model model) {
        Optional<Customer> customerOpt = customerService.getCustomerById(id);

        if (customerOpt.isPresent()) {
            Customer customer = customerOpt.get();
            int totalOrders = orderService.countByCustomer(Optional.of(customer));
            int distinctProducts = orderDetailService.countDistinctProductsByCustomer(customer);
            BigDecimal totalSpent = orderService.calculateTotalSpentByCustomer(customer);

            model.addAttribute("customer", customer);
            model.addAttribute("totalOrders", totalOrders);
            model.addAttribute("distinctProducts", distinctProducts);
            model.addAttribute("totalSpent", totalSpent);
        } else {
            // Handle the case where the customer is not found
            model.addAttribute("error", "Customer not found");
        }

        return "admin/customer_details";
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