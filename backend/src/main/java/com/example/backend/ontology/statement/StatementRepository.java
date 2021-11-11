package com.example.backend.ontology.statement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StatementRepository extends JpaRepository<Statement,Long> {
}
