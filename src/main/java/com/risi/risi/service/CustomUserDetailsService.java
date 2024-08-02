package com.risi.risi.service;

import com.risi.risi.entity.UserEntity;
import com.risi.risi.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class CustomUserDetailsService implements UserDetailsService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity userEntity = userRepository.findByUserId(username)
                .orElseThrow(() -> new UsernameNotFoundException("User not found"));

        return User.builder()
                .username(userEntity.getUserId())
                .password(userEntity.getPassword())
//                .roles(userEntity.getRoles().toArray(new String[0])) // Assuming roles are stored in a collection
                .build();
    }
}
