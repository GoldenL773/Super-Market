package org.example.spring.supermarket.controller;

import org.example.spring.supermarket.entity.*;
import org.example.spring.supermarket.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

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

    // Dashboard
    @GetMapping("")
    public String dashboard(Model model) {
        // Will add dashboard statistics when services are implemented
        return "admin/dashboard";
    }


    // Categories Management
    @GetMapping("/categories")
    public String listCategories(Model model) {
        // model.addAttribute("categories", categoryService.findAll());
        return "admin/categories/list";
    }

    @GetMapping("/categories/new")
    public String newCategoryForm(Model model) {
        model.addAttribute("category", new Categories());
        return "admin/categories/form";
    }

    @PostMapping("/categories")
    public String saveCategory(@ModelAttribute Categories category, RedirectAttributes redirectAttributes) {
        // categoryService.save(category);
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
        // model.addAttribute("order", orderService.findById(id));
        // model.addAttribute("orderDetails", orderService.getOrderDetails(id));
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
    public String listCustomers(Model model) {
        // model.addAttribute("customers", customerService.findAll());
        return "admin/customers/list";
    }

    @GetMapping("/customers/{id}")
    public String viewCustomer(@PathVariable int id, Model model) {
        // model.addAttribute("customer", customerService.findById(id));
        // model.addAttribute("orders", orderService.findByCustomerId(id));
        return "admin/customers/details";
    }

    // Inventory Management
    @GetMapping("/inventory")
    public String listInventory(Model model) {
        // model.addAttribute("inventory", inventoryService.findAll());
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
        // inventoryService.save(stockEntry);
        redirectAttributes.addFlashAttribute("message", "Inventory updated successfully!");
        return "redirect:/admin/inventory";
    }

    // Staff Management
    @GetMapping("/staff")
    public String listStaff(Model model) {
        // model.addAttribute("staffList", staffService.findAll());
        return "admin/staff/list";
    }

    @GetMapping("/staff/new")
    public String newStaffForm(Model model) {
        model.addAttribute("staff", new Staff());
        return "admin/staff/form";
    }

    @PostMapping("/staff")
    public String saveStaff(@ModelAttribute Staff staff, RedirectAttributes redirectAttributes) {
        // staffService.save(staff);
        redirectAttributes.addFlashAttribute("message", "Staff saved successfully!");
        return "redirect:/admin/staff";
    }

    // Suppliers Management
    @GetMapping("/suppliers")
    public String listSuppliers(Model model) {
        // model.addAttribute("suppliers", supplierService.findAll());
        return "admin/suppliers/list";
    }

    @GetMapping("/suppliers/new")
    public String newSupplierForm(Model model) {
        model.addAttribute("supplier", new Supplier());
        return "admin/suppliers/form";
    }

    @PostMapping("/suppliers")
    public String saveSupplier(@ModelAttribute Supplier supplier, RedirectAttributes redirectAttributes) {
        // supplierService.save(supplier);
        redirectAttributes.addFlashAttribute("message", "Supplier saved successfully!");
        return "redirect:/admin/suppliers";
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
        // model.addAttribute("salesData", orderService.getSalesReportData());
        return "admin/reports/sales";
    }

    @GetMapping("/reports/inventory")
    public String inventoryReport(Model model) {
        // model.addAttribute("inventoryData", inventoryService.getInventoryReportData());
        return "admin/reports/inventory";
    }
}