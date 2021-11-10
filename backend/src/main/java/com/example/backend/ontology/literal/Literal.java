package com.example.backend.ontology.literal;

import com.example.backend.ontology.statement.Statement;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "literals")
@NoArgsConstructor
@AllArgsConstructor
public class Literal {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String value;

    private String dataType;

    @OneToMany(mappedBy = "literal")
    private List<Statement> statements;
}
