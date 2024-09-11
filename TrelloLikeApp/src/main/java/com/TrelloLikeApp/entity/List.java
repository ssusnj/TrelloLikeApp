package com.TrelloLikeApp.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import java.util.Set;

@Data
@Builder
@Entity
public class List {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;

    @ManyToOne
    @JoinColumn(name="board_id")
    private Board board;

    @OneToMany(mappedBy = "list", cascade = CascadeType.ALL, orphanRemoval = true)
    private Set<Card> cards;
}
