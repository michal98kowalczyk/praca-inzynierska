package com.example.backend.ontology.verb;

import com.example.backend.ontology.statement.Statement;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "verbs")
@NoArgsConstructor
@AllArgsConstructor
public class Verb {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String verb;

    @OneToMany(mappedBy = "predicate")
    private List<Statement> statements;
}
