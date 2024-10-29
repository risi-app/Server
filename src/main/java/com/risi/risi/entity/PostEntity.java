package com.risi.risi.entity;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

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

    @OneToMany(mappedBy = "post", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<CommentEntity> comments = new ArrayList<>();

    @ElementCollection
    private Set<String> likedByUsers = new HashSet<>();

    public void toggleLike(String userId) {
        if (likedByUsers.contains(userId)) {
            likedByUsers.remove(userId);
        } else {
            likedByUsers.add(userId);
        }
    }

    public boolean isLikedByUser(String userId) {
        return likedByUsers.contains(userId);
    }

    public int getLikesCount() {
        return likedByUsers.size();
    }

}
