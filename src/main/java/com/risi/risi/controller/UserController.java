package com.risi.risi.controller;

import com.risi.risi.entity.UserEntity;
import com.risi.risi.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("api/user")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping("/check/{userId}/{userPw}")
    public UserEntity checkUser(@PathVariable String userId, @PathVariable String userPw) {
        return userService.checkUser(userId, userPw);
    }

    @GetMapping("/{userId}")
    public UserEntity getUser(@PathVariable String userId) {
        return userService.getUser(userId);
    }

    @PostMapping("/register")
    public void registerUser(@RequestBody UserEntity userEntity) {
        userService.registerUser(userEntity);
    }

    @PutMapping("/update/{userId}")
    public UserEntity updateUser(
            @PathVariable String userId,
            @RequestParam(value = "file", required = false) MultipartFile file,
            @RequestParam(value = "description", required = false) String description) {
        return userService.updateUser(userId, file, description);
    }


    @DeleteMapping("/delete/{userId}/{userPw}")
    public void deleteUser(@PathVariable String userId, @PathVariable String userPw) {
        userService.deleteUser(userId, userPw);
    }


    @PostMapping("/follow")
    public void followUser(@PathVariable String fromUserId, @PathVariable String toUserId) {
        userService.followUser(fromUserId, toUserId);
    }

    @PostMapping("/deleteUserFromFollowers")
    public void deleteUserFromFollower(@PathVariable String fromUserId, @PathVariable String toUserId) {
        userService.deleteUserFromFollowers(fromUserId, toUserId);
    }
}
