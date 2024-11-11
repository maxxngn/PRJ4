package com.example.fashion2.repository;

import com.example.fashion2.model.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Integer> {
    // Find comments by product ID
    List<Comment> findByProductId(int productId);
}
