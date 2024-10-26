package com.risi.risi.controller;

import com.risi.risi.entity.CommentEntity;
import com.risi.risi.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/comment")
public class CommentController {

    @Autowired
    CommentService commentService;

    @PostMapping("/add/{postId}/{userId}")
    public CommentEntity addComment(
            @PathVariable int postId,
            @PathVariable String userId,
            @RequestBody String content) {
        return commentService.addComment(userId, postId, content);
    }

    @PutMapping("/update/{commentId}")
    public CommentEntity updateComment(
            @PathVariable int commentId,
            @RequestBody String content) {
        return commentService.updateComment(commentId, content);
    }

    @DeleteMapping("/delete/{commentId}")
    public void deleteComment(@PathVariable int commentId) {
        commentService.deleteComment(commentId);
    }

    @PostMapping("/like/{commentId}")
    public void likeComment(@PathVariable int commentId) {
        commentService.likeComment(commentId);
    }
}
