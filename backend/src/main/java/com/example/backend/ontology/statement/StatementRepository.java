package com.example.backend.ontology.statement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface StatementRepository extends JpaRepository<Statement,Long> {

    List<Statement> findAllByModelId(Long id);
}
