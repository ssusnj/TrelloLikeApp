package com.TrelloLikeApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.List;

@Data
@Entity
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    private Long id;

    @NotEmpty(message = "Username is required.")
    @Column(unique = true)
    private String username;

    @ManyToOne
    @JoinColumn(name = "role_id") // FK in Role
    private Role role;

    @NotEmpty(message = "Password is required.")
    @Size(min = 5, message = "Password must be at least 5 characters long.")
    private String password;

    @OneToMany(mappedBy = "createdBy")
    private List<Board> createdBoards;

}