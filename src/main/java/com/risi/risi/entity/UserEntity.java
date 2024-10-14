package com.risi.risi.entity;

import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

import java.util.List;

@Table(name = "user")
@Entity
@Data
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(unique = true)
    private String userId;
    private String username;
    private String password;

    @Column(columnDefinition = "TEXT") // This allows storing long descriptions
    private String description = "";

    private String image = "";
    private String role;
    private String token;

    @OneToMany(mappedBy = "user", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonManagedReference
    @EqualsAndHashCode.Exclude
    @ToString.Exclude
    private List<PostEntity> posts;
    private List<String> followers;
    private List<String> following;

    public void addPost(PostEntity post) {
        posts.add(post);
        post.setUser(this);
    }

    public void deletePost(PostEntity post) {
        posts.remove(post);
        post.setUser(this);
    }
}
