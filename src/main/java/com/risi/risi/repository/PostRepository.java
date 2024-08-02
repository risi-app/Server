package com.risi.risi.repository;

import com.risi.risi.entity.PostEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostRepository extends JpaRepository<PostEntity, Integer> {
}
