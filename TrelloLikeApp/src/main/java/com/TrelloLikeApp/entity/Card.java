package com.TrelloLikeApp.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
@Entity
public class Card {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @ManyToOne
    @JoinColumn(name = "list_id")
    private List list;

    @ManyToOne
    @JoinColumn(name="user_id")
    private User assignedUser;
}
