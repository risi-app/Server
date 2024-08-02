package com.risi.risi.service;

import com.risi.risi.entity.PostEntity;
import com.risi.risi.entity.UserEntity;
import com.risi.risi.repository.PostRepository;
import com.risi.risi.repository.UserRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.Optional;

@Service
public class PostService {

    @Autowired
    private PostRepository postRepository;

    @Autowired
    private UserRepository userRepository;

    @Transactional
    public PostEntity uploadPost(String userId, String title, String description, MultipartFile file) {
        Optional<UserEntity> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            PostEntity post = new PostEntity();
            post.setTitle(title);
            post.setDescription(description);
            post.setUsername(user.getUsername());
            post.setProfile(user.getImage());

            if (file != null && !file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    String filename = file.getOriginalFilename();
                    Path path = Paths.get("C:/Users/user/Desktop/tmppic/" + filename);
                    Files.write(path, bytes);
                    post.setImage("/uploads/" + filename); // Store the relative URL path
                } catch (IOException e) {
                    throw new RuntimeException("Error saving file", e);
                }
            }

            user.addPost(post);
            userRepository.save(user);
            return post;
        } else {
            throw new RuntimeException("User not found with userId: " + userId);
        }
    }

    @Transactional
    public PostEntity updatePost(int postId, String title, String description, MultipartFile file) {
        Optional<PostEntity> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            PostEntity post = postOptional.get();
            post.setTitle(title);
            post.setDescription(description);

            if (file != null && !file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    String filename = file.getOriginalFilename();
                    Path path = Paths.get("C:/Users/user/Desktop/tmppic/" + filename);
                    Files.write(path, bytes);
                    post.setImage("/uploads/" + filename); // Store the relative URL path
                } catch (IOException e) {
                    throw new RuntimeException("Error saving file", e);
                }
            }

            return postRepository.save(post);
        } else {
            throw new RuntimeException("Post not found with postId: " + postId);
        }
    }


    @Transactional
    public void deletePost(int postId) {
        Optional<PostEntity> postOptional = postRepository.findById(postId);
        if (postOptional.isPresent()) {
            postRepository.delete(postOptional.get());
        } else {
            throw new RuntimeException("Post not found with postId: " + postId);
        }
    }


    public List<PostEntity> getPosts() {
        return postRepository.findAll();
    }

    public PostEntity getPost(int postId) {
        Optional<PostEntity> postOptional = postRepository.findById(postId);
        return postOptional.orElse(null);
    }
}
