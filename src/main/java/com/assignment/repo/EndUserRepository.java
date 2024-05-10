package com.assignment.repo;

import com.assignment.model.EndUser;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface EndUserRepository extends JpaRepository<EndUser, Long> {
    Optional<EndUser> findByUsername(String username);

    Optional<EndUser> findByEmail(String email);
}
