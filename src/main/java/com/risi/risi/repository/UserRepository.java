package com.risi.risi.repository;

import com.risi.risi.entity.UserEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<UserEntity, Integer> {
    Optional<UserEntity> findByUserId(String userId);
}
