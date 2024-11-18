package com.example.fashion2.service;

import com.example.fashion2.model.Comment;
import com.example.fashion2.model.Product;
import com.example.fashion2.model.User;
import com.example.fashion2.repository.CommentRepository;
import com.example.fashion2.repository.ProductRepository;
import com.example.fashion2.repository.UserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;
    
    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private UserRepository userRepository;

    // Create a new comment
    public Comment createComment(Comment comment) {
        // print payload
        System.out.println("xx" + comment);
        // Lấy product từ database
        Product product = productRepository.findById(comment.getProduct().getId())
                .orElseThrow(() -> new RuntimeException("Product not found"));
        comment.setProduct(product);

        // Lấy user từ database
        User user = userRepository.findById(comment.getUser().getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
        comment.setUser(user);

        // Lưu comment
        return commentRepository.save(comment);
    }

    // List comments by product ID
    public List<Comment> getCommentsByProductId(int productId) {
        // Fetch comments from the repository
        List<Comment> comments = commentRepository.findByProductId(productId);

        // Sort comments by ID in descending order (LIFO)
        comments.sort(Comparator.comparing(Comment::getId).reversed());

        return comments;
    }
}
