package org.example.spring.supermarket.controller;


import jakarta.servlet.http.HttpSession;
import org.example.spring.supermarket.DTO.CategoryDTO;
import org.example.spring.supermarket.DTO.ProductDTO;
import org.example.spring.supermarket.entity.Customer;
import org.example.spring.supermarket.service.CartService;
import org.example.spring.supermarket.service.CategoryService;
import org.example.spring.supermarket.service.CustomerService;
import org.example.spring.supermarket.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Map;


@Controller
public class HomeController {
    private CategoryService categoryService;
    private ProductService productService;
    private CustomerService customerService;
    private CartService cartService;
    private static final Logger logger = LoggerFactory.getLogger(HomeController.class);

    public HomeController(CategoryService categoryService,
                          ProductService productService,
                          CustomerService customerService,
                          CartService cartService) {
        this.categoryService = categoryService;
        this.productService = productService;
        this.customerService = customerService;
        this.cartService = cartService;
    }

    @GetMapping({"/", "/home"})
    public String home(Model model, HttpSession session) {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        org.springframework.security.core.userdetails.User user =
                (org.springframework.security.core.userdetails.User) authentication.getPrincipal();

        // Fetch customer using the authenticated username
        Customer c = customerService.findByUsername(user.getUsername());

        Map<ProductDTO, Integer> cartItems = cartService.getCartItems(c);
        double total = cartService.getCartTotal(c);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        session.setAttribute("customer", c);
        List<CategoryDTO> categoryDTOList = categoryService.getAll();
        logger.warn("Debug Category:" + categoryDTOList.size());
        logger.warn("Debug Category:" + categoryDTOList.get(1).getImage());
        model.addAttribute("categories", categoryDTOList);

        return "index";
    }

    @GetMapping(value = "/shop")
    public String shop(@RequestParam(required = false) String id,
                       @RequestParam(required = false) String query,
                       @RequestParam(required = false) String sortByPrice,
                       @RequestParam(required = false) String sortByDate,
                       @RequestParam(defaultValue = "price") String sortBy,
                       @RequestParam(defaultValue = "asc") String sortDirection,
                       @RequestParam(defaultValue = "0") int page,
                       @RequestParam(defaultValue = "9") int size,
                       Model model, HttpSession session) {
        if (sortByPrice != null && !sortByPrice.isEmpty()) {
            sortBy = "price";
            sortDirection = sortByPrice;
        } else if (sortByDate != null && !sortByDate.isEmpty()) {
            sortBy = "created_at";
            sortDirection = sortByDate;
        }

        // Tạo Sort và Pageable
        Sort sort = Sort.by(sortDirection.equals("asc") ? Sort.Direction.ASC : Sort.Direction.DESC, sortBy);
        Pageable pageable = PageRequest.of(page, size, sort);

        // Khởi tạo productDTOPage
        Page<ProductDTO> productDTOPage = null;
        List<CategoryDTO> categoryDTOList = categoryService.getAll();

        // Logic xử lý tìm kiếm và lọc
        if (query != null && !query.isEmpty()) {
            if (id != null && !id.isEmpty()) { // Check for empty string here
                productDTOPage = productService.searchProductsByCategoryAndQuery(query, Integer.parseInt(id), pageable);
            } else {
                productDTOPage = productService.searchProducts(query, pageable);
            }
            model.addAttribute("searchQuery", query);
        } else if (id != null && !id.isEmpty()) { // Check for empty string here
            productDTOPage = productService.searchProductsByCategory(Integer.parseInt(id), pageable);
        } else {
            productDTOPage = productService.sortProducts(pageable);
        }
        Customer customer = (Customer) session.getAttribute("customer");
        Map<ProductDTO, Integer> cartItems = cartService.getCartItems(customer);
        double total = cartService.getCartTotal(customer);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        model.addAttribute("id", id);
        model.addAttribute("searchQuery", query);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", productDTOPage.getTotalPages());
        model.addAttribute("sortBy", sortBy);
        model.addAttribute("sortDirection", sortDirection);
        model.addAttribute("categories", categoryDTOList);
        model.addAttribute("products", productDTOPage.getContent());
        return "shop";
    }

    @GetMapping("/detail")
    public String detailProduct(@RequestParam("id") int id, Model model) {
        ProductDTO productDTO = productService.getById(id);
        List<ProductDTO> list = productService.getProductRandomByCategory(id);
        model.addAttribute("product", productDTO);
        model.addAttribute("productRelative", list);
        return "detail-product";
    }

    @GetMapping("/nice-table")
    public String table() {
        return "/quixlab-master/widgets";
    }

    @GetMapping("/setting")
    public String showSetting(Model model, HttpSession session) {
        Customer customer = (Customer) session.getAttribute("customer");
        Map<ProductDTO, Integer> cartItems = cartService.getCartItems(customer);
        double total = cartService.getCartTotal(customer);
        model.addAttribute("cartItems", cartItems);
        model.addAttribute("total", total);
        return "setting";
    }
}


