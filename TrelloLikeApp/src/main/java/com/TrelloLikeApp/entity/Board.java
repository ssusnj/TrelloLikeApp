package com.TrelloLikeApp.entity;

import jakarta.persistence.*;
import lombok.Data;

@Data // try builder
@Entity
public class Board {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "created_by") // FK in User
    private User createdBy;

  }