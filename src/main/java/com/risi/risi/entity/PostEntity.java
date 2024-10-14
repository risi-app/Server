package com.risi.risi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import jakarta.persistence.*;
import lombok.*;

@Table(name = "post")
@Entity
@Getter
@Setter
@ToString
@RequiredArgsConstructor
public class PostEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String profile;
    private String username;

    @Column(columnDefinition = "TEXT") // This allows storing long descriptions
    private String title;

    @Column(columnDefinition = "TEXT") // This allows storing long descriptions
    private String description;

    private String image;
    private int likes = 0;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    @JsonBackReference
    @ToString.Exclude
    private UserEntity user;
}
