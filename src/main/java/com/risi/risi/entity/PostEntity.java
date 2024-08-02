package com.risi.risi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.Data;

@Table(name = "post")
@Entity
@Data
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String profile;
    private String username;
    private String title;
    private String description;
    private String image;
    private int likes = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    private UserEntity user;
}
