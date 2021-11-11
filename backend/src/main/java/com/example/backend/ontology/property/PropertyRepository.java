package com.example.backend.ontology.property;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PropertyRepository extends JpaRepository<Property,Long> {
    List<Property> findAllByStatementId(Long statementId);
}
