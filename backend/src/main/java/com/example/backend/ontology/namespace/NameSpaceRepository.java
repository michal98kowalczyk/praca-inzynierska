package com.example.backend.ontology.namespace;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface NameSpaceRepository extends JpaRepository<NameSpace,Long> {
    Optional<NameSpace> findByName(String name);
}
