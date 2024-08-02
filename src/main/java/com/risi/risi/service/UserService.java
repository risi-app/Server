package com.risi.risi.service;

import com.risi.risi.common.JwtUtils;
import com.risi.risi.entity.PostEntity;
import com.risi.risi.entity.UserEntity;
import com.risi.risi.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Optional;

@Slf4j
@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    private JwtUtils jwtUtils;

    public UserEntity checkUser(String userId, String userPw) {
        Optional<UserEntity> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if (bCryptPasswordEncoder.matches(userPw, user.getPassword())) {
                String token = jwtUtils.generateToken(user);
                user.setToken(token);
                return user;
            }
        }
        return null;
    }

    public UserEntity getUser(String userId) {
        Optional<UserEntity> userOptional = userRepository.findByUserId(userId);
        return userOptional.orElse(null);
    }

    public void registerUser(UserEntity userEntity) {
        userEntity.setPassword(bCryptPasswordEncoder.encode(userEntity.getPassword()));
        userRepository.save(userEntity);
    }

    public UserEntity updateUser(String userId, MultipartFile file, String description) {
        Optional<UserEntity> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if (file != null && !file.isEmpty()) {
                try {
                    byte[] bytes = file.getBytes();
                    String filename = file.getOriginalFilename();
                    Path path = Paths.get("C:/Users/user/Desktop/tmppic/" + filename);
                    Files.createDirectories(path.getParent());
                    Files.write(path, bytes);
                    String imagePath = "/uploads/" + filename;
                    user.setImage(imagePath);

                    // Update the profile image for each post
                    for (PostEntity post : user.getPosts()) {
                        post.setProfile(imagePath);
                    }
                } catch (IOException e) {
                    throw new RuntimeException("Error saving file", e);
                }
            }
            if (description != null) {
                user.setDescription(description);
            }
            return userRepository.save(user);
        } else {
            throw new RuntimeException("User not found with userId: " + userId);
        }
    }

    public void deleteUser(String userId, String userPw) {
        Optional<UserEntity> userOptional = userRepository.findByUserId(userId);
        if (userOptional.isPresent()) {
            UserEntity user = userOptional.get();
            if (bCryptPasswordEncoder.matches(userPw, user.getPassword())) {
                userRepository.delete(user);
            } else {
                throw new RuntimeException("Incorrect password");
            }
        } else {
            throw new RuntimeException("User not found with userId: " + userId);
        }
    }

    public void followUser(String fromUserId, String toUserId) {
        Optional<UserEntity> fromUserOptional = userRepository.findByUserId(fromUserId);
        Optional<UserEntity> toUserOptional = userRepository.findByUserId(toUserId);
        if (toUserOptional.isPresent() && fromUserOptional.isPresent()) {
            UserEntity fromUser = fromUserOptional.get();
            UserEntity toUser = toUserOptional.get();

            fromUser.getFollowing().add(toUserId);
            toUser.getFollowers().add(fromUserId);
        }
    }

    public void deleteUserFromFollowers(String fromUserId, String toUserId) {
        Optional<UserEntity> fromUserOptional = userRepository.findByUserId(fromUserId);
        Optional<UserEntity> toUserOptional = userRepository.findByUserId(toUserId);
        if (toUserOptional.isPresent() && fromUserOptional.isPresent()) {
            UserEntity fromUser = fromUserOptional.get();
            UserEntity toUser = toUserOptional.get();

            fromUser.getFollowing().remove(toUserId);
            toUser.getFollowers().remove(fromUserId);
        }
    }
}
