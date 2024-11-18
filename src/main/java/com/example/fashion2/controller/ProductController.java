package com.example.fashion2.controller;

import com.example.fashion2.model.Product;
import com.example.fashion2.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "http://localhost:3000")
public class ProductController {

    @Autowired
    private ProductService productService;

    // 1. Create Product
    @PostMapping("/create")
    public ResponseEntity<Product> createProduct(@RequestBody Product product) {
        Product createdProduct = productService.createProduct(product);
        return new ResponseEntity<>(createdProduct, HttpStatus.CREATED);
    }

    @GetMapping("/all")
    public ResponseEntity<Page<Product>> getAllProducts(
            @RequestParam(required = false) String name,
            @RequestParam(required = false) String gender,
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "0") int page, // Default to page 0
            @RequestParam(defaultValue = "10") int size, // Default to size 10
            @RequestParam(required = false) String sortDirection) { // Optional sortDirection
    
        // Default to "desc" and sort by "id" if sortDirection is missing or invalid
        String sortBy = "id";
        Sort sort = (sortDirection != null && sortDirection.equalsIgnoreCase("asc")) 
                        ? Sort.by(sortBy).ascending()
                        : Sort.by(sortBy).descending();
    
        Page<Product> products = productService.searchProducts(name, gender, category, page, size, sort);
        return ResponseEntity.ok(products);
    }    

    // 3. Get Product by ID
    @GetMapping("/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable int id) {
        Product product = productService.getProductById(id);
        if (product != null) {
            return ResponseEntity.ok(product);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    // 5. Delete Product
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable int id) {
        // Thực hiện xóa mềm hoặc hủy xóa
        boolean isDeleted = productService.deleteProduct(id);

        if (isDeleted == true) {
            return ResponseEntity.ok("Product deleted successfully");
        } else {
            return ResponseEntity.ok("Product restore successfully");
        }
    }

    @GetMapping("/gender/man")
    public ResponseEntity<List<Product>> getProductsForMen(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        List<Product> products = productService.getProductsForMen(category, sortDirection);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/gender/women")
    public ResponseEntity<List<Product>> getProductsForWomen(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        List<Product> products = productService.getProductsForWomen(category, sortDirection);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/gender/kid")
    public ResponseEntity<List<Product>> getProductsForKids(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        List<Product> products = productService.getProductsForKids(category, sortDirection);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/gender/unisex")
    public ResponseEntity<List<Product>> getProductsForUnisex(
            @RequestParam(required = false) String category,
            @RequestParam(defaultValue = "asc") String sortDirection) {
        List<Product> products = productService.getProductsForUnisex(category, sortDirection);
        return ResponseEntity.ok(products);
    }

    // 4. Update Product
    @PutMapping("/update/{id}")
    public ResponseEntity<Product> updateProduct(@PathVariable int id, @RequestBody Product product) {
        Product updatedProduct = productService.updateProduct(id, product);
        if (updatedProduct != null) {
            return ResponseEntity.ok(updatedProduct);
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }
}
