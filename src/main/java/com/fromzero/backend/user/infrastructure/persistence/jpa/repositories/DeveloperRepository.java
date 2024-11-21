package com.fromzero.backend.user.infrastructure.persistence.jpa.repositories;

import com.fromzero.backend.user.domain.model.aggregates.Developer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeveloperRepository extends JpaRepository<Developer, Long>{
    Optional<Developer> findDeveloperByUser_Id(Long userId);
}
