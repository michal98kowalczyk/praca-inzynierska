package com.example.backend.ontology.statement;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface StatementRepository extends JpaRepository<Statement,Long> {

    List<Statement> findAllByModelId(Long id);
    List<Statement> findAllByPredicateId(Long id);
    List<Statement> findAllByResourceId(Long id);
    List<Statement> findAllBySubjectId(Long id);
}
