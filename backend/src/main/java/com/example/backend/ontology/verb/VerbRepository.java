package com.example.backend.ontology.verb;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface VerbRepository extends JpaRepository<Verb,Long> {
    Optional<Verb> findByVerb(String verb);
}
