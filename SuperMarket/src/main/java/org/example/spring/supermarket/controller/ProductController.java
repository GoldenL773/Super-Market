package org.example.spring.supermarket.controller;

import jakarta.validation.Valid;
import org.example.spring.supermarket.DTO.productDTO;
import org.example.spring.supermarket.entity.Categories;
import org.example.spring.supermarket.entity.Inventory;
import org.example.spring.supermarket.entity.Product;
import org.example.spring.supermarket.repository.CategoriesRepository;
import org.example.spring.supermarket.repository.InventoryRepository;
import org.example.spring.supermarket.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.naming.Binding;
import java.io.File;
import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.*;
import java.util.stream.Collectors;

@Controller
@RequestMapping("/products")
public class ProductController {
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private CategoriesRepository categoriesRepository;

    @Autowired
    private InventoryRepository inventoryRepository;

    @GetMapping
    public String showAllProducts(Model model) {
        List<Product> products = productRepository.findAll();
        List<Categories> categories = categoriesRepository.findAll();
        List<Object[]> inventoryData = inventoryRepository.findAllProductQuantities();

        // Chuyển đổi kết quả về Map<Integer, Integer>
        Map<Integer, Integer> productQuantities = inventoryData.stream()
                .collect(Collectors.toMap(
                        data -> (Integer) data[0],  // productId
                        data -> ((Number) data[1]).intValue() // Chuyển đổi Long thành Integer
                ));

        model.addAttribute("products", products);
        model.addAttribute("categories", categories);
        model.addAttribute("productQuantities", productQuantities);
        return "products/listProduct"; // Đảm bảo đường dẫn template đúng
    }


    @GetMapping("/create")
    public String createProduct(Model model) {
        productDTO productDTO =  new productDTO();
        List<Categories> categories = categoriesRepository.findAll();
        model.addAttribute("productDTO", productDTO);
        model.addAttribute("categories", categories);
        System.out.println("Số lượng danh mục: " + categories.size()); // Debug
        return "products/createProduct";
    }
    @PostMapping("/create")
    public String createProduct(
            @Valid @ModelAttribute("productDTO") productDTO productDTO,
            BindingResult result,
            Model model) {

        System.out.println("🔹 Request received for createProduct!");

        if (result.hasErrors()) {
            System.out.println("🔸 Validation errors: " + result.getAllErrors());
            List<Categories> categories = categoriesRepository.findAll();
            model.addAttribute("categories", categories);
            return "products/createProduct";
        }

        Date createAt = new Date();

        // Kiểm tra danh mục có tồn tại không
        Categories category = categoriesRepository.findById(productDTO.getCategoryId())
                .orElse(null);
        if (category == null) {
            result.rejectValue("categoryId", "error.productDTO", "Danh mục không tồn tại!");
            return "products/createProduct";
        }

        // Chuyển đổi từ DTO sang Entity
        Product product = new Product();
        product.setName(productDTO.getName());
        product.setDescription(productDTO.getDescription());
        product.setPrice(productDTO.getPrice());
        product.setCreatedAt(createAt);
        product.setCategory(category);

        // Xử lý upload ảnh
        MultipartFile imageFile = productDTO.getImage();
        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                // Đường dẫn thư mục lưu ảnh (tuyệt đối)
                String uploadDir = "src/main/resources/static/images/";
                File uploadFolder = new File(uploadDir);
                if (!uploadFolder.exists()) {
                    uploadFolder.mkdirs(); // Tạo thư mục nếu chưa tồn tại
                }

                // Tạo tên file ảnh duy nhất để tránh ghi đè
                String fileName = System.currentTimeMillis() + "_" + imageFile.getOriginalFilename();
                Path filePath = Path.of(uploadDir, fileName);

                // Lưu file ảnh vào thư mục
                Files.copy(imageFile.getInputStream(), filePath, StandardCopyOption.REPLACE_EXISTING);

                // Lưu đường dẫn ảnh vào database
                product.setImage("/images/" + fileName);

                System.out.println("✅ Ảnh đã lưu thành công: " + filePath);
            } catch (IOException e) {
                throw new RuntimeException("❌ Lỗi khi lưu ảnh: " + e.getMessage(), e);
            }
        }

        // Lưu sản phẩm vào database
        product = productRepository.save(product);

        // Kiểm tra dữ liệu đầu vào của Inventory
        if (productDTO.getQuantity() != null && productDTO.getQuantity() >= 0 &&
                productDTO.getPurchasePrice() != null && productDTO.getPurchasePrice().compareTo(BigDecimal.ZERO) >= 0)
        {

            // Tạo một Inventory mới để theo dõi số lượng sản phẩm
            Inventory inventory = new Inventory();
            inventory.setProduct(product);
            inventory.setQuantity(productDTO.getQuantity());
            inventory.setPurchaseDate(createAt);
            inventory.setPurchasePrice(productDTO.getPrice());
            inventory.setLastUpdated(createAt);

            // Lưu Inventory vào cơ sở dữ liệu
            inventoryRepository.save(inventory);
            System.out.println("✅ Inventory saved: " + inventory.getQuantity() + " items for " + product.getName());
        } else {
            System.out.println("⚠️ Cảnh báo: Giá nhập hoặc số lượng không hợp lệ.");
        }

        return "redirect:/products";
    }


    @GetMapping("/delete")
    public String deleteProduct(@RequestParam int id) {
        try{
            Product product = productRepository.findById(id).get();
            //delete image
            Path path = Paths.get("src/main/resources/static/images/" + product.getImage());
            try{
                Files.delete(path);
            } catch (IOException e) {
                System.out.println(e.getMessage());
            }
            //delete
            productRepository.delete(product);
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
        return "redirect:/products";
    }

    @GetMapping("/detail/{id}")
    public String viewProduct(@PathVariable("id") int id, Model model) {
        Optional<Product> productOpt = productRepository.findById(id);

        if (productOpt.isPresent()) {
            Product product = productOpt.get();

            // Lấy danh sách số lượng sản phẩm từ InventoryRepository
            List<Object[]> inventoryData = inventoryRepository.findAllProductQuantities();

            // Tạo map để lấy quantity của sản phẩm hiện tại
            Map<Integer, Integer> productQuantities = inventoryDataToMap(inventoryData);

            int quantity = productQuantities.getOrDefault(product.getId(), 0); // Lấy số lượng hoặc gán 0 nếu không có

            model.addAttribute("product", product);
            model.addAttribute("productQuantity", quantity); // Thêm số lượng vào model
            return "products/productDetail";
        } else {
            return "redirect:/products";
        }
    }

    // Phương thức chuyển danh sách object[] thành Map<Integer, Integer>
    private Map<Integer, Integer> inventoryDataToMap(List<Object[]> inventoryData) {
        return inventoryData.stream().collect(Collectors.toMap(
                data -> (Integer) data[0], // productId
                data -> ((Number) data[1]).intValue() // Quantity
        ));
    }


}
