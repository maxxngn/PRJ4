package com.example.fashion2.service;

import com.example.fashion2.dto.CreateProductRequest;
import com.example.fashion2.dto.UpdateProductRequest;
import com.example.fashion2.model.*;
import com.example.fashion2.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private ProductColorRepository colorRepository;

    @Autowired
    private ProductSizeRepository sizeRepository;

    @Autowired
    private ProductImageRepository imageRepository;

    @Transactional
    public Product createProduct(CreateProductRequest request) {
        // Tạo sản phẩm mới
        Product product = new Product();
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQty(request.getQty());
        product.setGender(request.getGender());
        product.setStatus(request.isStatus());

        // Lưu sản phẩm
        product = productRepository.save(product);

        // Thêm màu sắc, kích thước và hình ảnh
        saveColorsSizesImages(request, product);

        return product;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Product getProductById(int id) {
        return productRepository.findById(id).orElse(null);
    }

    @Transactional
    public Product updateProduct(int id, UpdateProductRequest request) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));

        // Cập nhật thông tin sản phẩm
        product.setName(request.getName());
        product.setDescription(request.getDescription());
        product.setPrice(request.getPrice());
        product.setQty(request.getQty());
        product.setGender(request.getGender());
        product.setStatus(request.isStatus());

        // Xóa các thông tin cũ
        colorRepository.deleteByProductId(id);
        sizeRepository.deleteByProductId(id);
        imageRepository.deleteByProductId(id);

        // Thêm lại màu sắc, kích thước và hình ảnh mới
        saveColorsSizesImages(request, product);

        return productRepository.save(product);
    }

    @Transactional
    public void deleteProduct(int id) {
        Product product = productRepository.findById(id).orElseThrow(() -> new RuntimeException("Product not found"));
        productRepository.delete(product);
    }

    private void saveColorsSizesImages(Object request, Product product) {
        List<String> colors;
        List<String> sizes;
        List<String> images;
    
        if (request instanceof CreateProductRequest) {
            colors = ((CreateProductRequest) request).getColors();
            sizes = ((CreateProductRequest) request).getSizes();
            images = ((CreateProductRequest) request).getImages();
        } else if (request instanceof UpdateProductRequest) {
            colors = ((UpdateProductRequest) request).getColors();
            sizes = ((UpdateProductRequest) request).getSizes();
            images = ((UpdateProductRequest) request).getImages();
        } else {
            throw new IllegalArgumentException("Invalid request type");
        }
    
        // Save colors
        for (String colorName : colors) {
            ProductColor color = new ProductColor();
            color.setProduct(product);
            color.setColor(colorName);
            colorRepository.save(color);
        }
    
        // Save sizes
        for (String sizeName : sizes) {
            ProductSize size = new ProductSize();
            size.setProduct(product);
            size.setSize(sizeName);
            sizeRepository.save(size);
        }
    
        // Save images
        for (String imageUrl : images) {
            ProductImage image = new ProductImage();
            image.setProduct(product);
            image.setImageUrl(imageUrl);
            imageRepository.save(image);
        }
    }    
}
