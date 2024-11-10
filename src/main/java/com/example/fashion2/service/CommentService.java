package com.example.fashion2.service;

import com.example.fashion2.model.Comment;
import com.example.fashion2.repository.CommentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties.Sort;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;

@Service
public class CommentService {

    @Autowired
    private CommentRepository commentRepository;

    // Create a new comment
    public Comment createComment(Comment comment) {
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
