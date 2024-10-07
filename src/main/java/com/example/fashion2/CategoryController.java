package com.example.fashion2;

import com.example.fashion2.model.Category;
import com.example.fashion2.repository.CategoryRepository; // Đảm bảo rằng bạn đã tạo repository cho Category
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.util.List;

@Controller
@RequestMapping("/admin/categories")
public class CategoryController {
    
    @Autowired
    private CategoryRepository categoryRepository;

    @GetMapping
    public String listCategories(Model model) {
        List<Category> categories = categoryRepository.findAll();
        model.addAttribute("content", "categories/list");
        model.addAttribute("categories", categories);
        return "layout/adminLayout"; // Trỏ tới trang danh sách danh mục
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("category", new Category());
        return "categories/create"; // Trỏ tới trang tạo danh mục mới
    }

    @PostMapping
    public String createCategory(@ModelAttribute Category category) {
        category.setDeletedAt(null); // Thiết lập deletedAt mặc định là null
        categoryRepository.save(category);
        return "redirect:/admin/categories"; // Chuyển hướng về danh sách danh mục
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Integer id, Model model) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        model.addAttribute("category", category);
        return "categories/edit"; // Trỏ tới trang chỉnh sửa danh mục
    }

    @PostMapping("/{id}")
    public String updateCategory(@PathVariable Integer id, @ModelAttribute Category category) {
        category.setId(id); // Thiết lập ID cho đối tượng danh mục
        categoryRepository.save(category);
        return "redirect:/admin/categories"; // Chuyển hướng về danh sách danh mục
    }

    @GetMapping("/{id}/delete")
    public String deleteCategory(@PathVariable Integer id) {
        Category category = categoryRepository.findById(id).orElseThrow(() -> new IllegalArgumentException("Invalid category Id:" + id));
        category.setDeletedAt(new Timestamp(System.currentTimeMillis())); // Đánh dấu là đã xóa
        categoryRepository.save(category);
        return "redirect:/admin/categories"; // Chuyển hướng về danh sách danh mục
    }
}
