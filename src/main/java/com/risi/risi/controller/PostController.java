package com.risi.risi.controller;

import com.risi.risi.entity.PostEntity;
import com.risi.risi.service.PostService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@RestController
@RequestMapping("/api/post")
public class PostController {

    @Autowired
    private PostService postService;

    @GetMapping("/get")
    public List<PostEntity> getPosts() {
        return postService.getPosts();
    }

    @GetMapping("/get/{postId}")
    public PostEntity getPost(@PathVariable int postId) {
        return postService.getPost(postId);
    }

    @PostMapping("/{userId}")
    public PostEntity uploadPost(
            @PathVariable String userId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam("file") MultipartFile file) {
        try {
            return postService.uploadPost(userId, title, description, file);
        } catch (Exception e) {
            // Log the error for debugging purposes
            e.printStackTrace();
            throw new RuntimeException("Error uploading post", e);
        }
    }

    @PutMapping("/update/{postId}")
    public PostEntity updatePost(
            @PathVariable int postId,
            @RequestParam("title") String title,
            @RequestParam("description") String description,
            @RequestParam(value = "file", required = false) MultipartFile file) {
        try {
            return postService.updatePost(postId, title, description, file);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("Error updating post", e);
        }
    }

    @DeleteMapping("/delete/{postId}")
    public void deletePost(@PathVariable int postId) {
        postService.deletePost(postId);
    }
}
