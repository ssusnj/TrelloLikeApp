package com.TrelloLikeApp.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String description;

    @OneToMany(mappedBy = "board", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<List> lists;

    @ManyToMany
    @JoinTable(
            name="board_user",
            joinColumns = {@JoinColumn(name = "board_id")},
            inverseJoinColumns = {@JoinColumn(name = "user_id")}
    )
    private Set<User> assignedUsers;

  }