package com.TrelloLikeApp.repositories;

import com.TrelloLikeApp.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface RoleRepository extends JpaRepository<Role, Long> {

    Optional<Role> findByRoleType(Role.RoleType roleType);

}