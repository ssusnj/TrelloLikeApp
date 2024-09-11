package com.TrelloLikeApp.repositories;

import com.TrelloLikeApp.entity.List;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface ListRepository extends JpaRepository<List, Long> {
    Optional<Set<List>> findAllByBoard_Id(Long boardId);
}
