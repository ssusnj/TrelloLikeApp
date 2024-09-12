package com.TrelloLikeApp.repositories;

import com.TrelloLikeApp.entity.Board;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Set;

public interface BoardRepository extends JpaRepository<Board, Long> {

    Set<Board> findAllByCreatedBy_Id(Long userId);
}