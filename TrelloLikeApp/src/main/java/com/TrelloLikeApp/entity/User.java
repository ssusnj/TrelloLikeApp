package com.TrelloLikeApp.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.Size;
import lombok.Data;
import java.util.HashSet;
import java.util.Set;

@Data
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id", unique = true)
    private Long id;

    @NotEmpty(message = "Username is required.")
    @Column(unique = true, nullable = false)
    private String username;

    @Column(name = "first_name", nullable = false)
    @Size(max = 100)
    private String firstName;

    @Column(name = "last_name", nullable = false)
    @Size(max = 100)
    private String lastName;

    @ManyToOne
    @JoinColumn(name = "role_id")
    private Role role;

    @NotEmpty(message = "Password is required.")
    @Size(min = 5, message = "Password must be at least 5 characters long.")
    private String password;

    @ManyToMany(mappedBy = "assignedUsers", cascade = {CascadeType.PERSIST, CascadeType.MERGE})
    private Set<Board> boards = new HashSet<>();

    @OneToMany(mappedBy = "assignedUser")
    private Set<Card> assignedCards = new HashSet<>();

}