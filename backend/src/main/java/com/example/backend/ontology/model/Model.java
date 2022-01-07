package com.example.backend.ontology.model;

import com.example.backend.ontology.statement.Statement;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "models")
@NoArgsConstructor
@AllArgsConstructor
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;
    @JsonManagedReference
    @OneToMany(mappedBy = "model")
    private List<Statement> statements;
}
