package com.TrelloLikeApp.repositories;

import com.TrelloLikeApp.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface BoardRepository extends JpaRepository<Board, Long> {

    List<Board> findAllByCreatedBy_Id(Long createdBy); // optional?

}