package com.backend.repositories;

import com.backend.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.Set;

public interface CardRepository extends JpaRepository<Card, Long> {

     Optional<Set<Card>> findAllByList_Id(Long listId);
     void deleteAllByList_Id(Long listId);

}