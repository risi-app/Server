package com.risi.risi.service;

import com.risi.risi.entity.CommentEntity;
import com.risi.risi.entity.PostEntity;
import com.risi.risi.entity.UserEntity;
import com.risi.risi.repository.CommentRepository;
import com.risi.risi.repository.PostRepository;
import com.risi.risi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CommentService {

    @Autowired
    CommentRepository commentRepository;

    @Autowired
    PostRepository postRepository;

    @Autowired
    UserRepository userRepository;

    public CommentEntity addComment(String userId, int postId, String content) {
        Optional<PostEntity> postOptional = postRepository.findById(postId);
        Optional<UserEntity> userOptional = userRepository.findByUserId(userId);

        if (postOptional.isPresent() && userOptional.isPresent()) {
            PostEntity post = postOptional.get();
            UserEntity user = userOptional.get();

            CommentEntity comment = new CommentEntity();
            comment.setContent(content);
            comment.setPost(post);
            comment.setUsername(user.getUsername());
            comment.setProfileImg(user.getImage());

            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Post or user not found");
        }
    }

    public CommentEntity updateComment(int commentId, String content) {
        Optional<CommentEntity> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            CommentEntity comment = commentOptional.get();
            comment.setContent(content);
            return commentRepository.save(comment);
        } else {
            throw new RuntimeException("Comment not found");
        }
    }

    public void deleteComment(int commentId) {
        Optional<CommentEntity> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            commentRepository.delete(commentOptional.get());
        } else {
            throw new RuntimeException("Comment not found");
        }
    }

    public void likeComment(int commentId) {
        Optional<CommentEntity> commentOptional = commentRepository.findById(commentId);
        if (commentOptional.isPresent()) {
            CommentEntity comment = commentOptional.get();
            comment.setLikes(comment.getLikes() + 1);
            commentRepository.save(comment);
        } else {
            throw new RuntimeException("Comment not found");
        }
    }
}
