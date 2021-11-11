package com.example.backend.ontology.literal;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface LiteralRepository extends JpaRepository<Literal,Long> {

    Optional<Literal> findByValue(String value);
    Optional<Literal> findByDataType(String dataType);


    Optional<Literal> findByValueAndDataType(String value, String dataType);
}
