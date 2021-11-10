package com.example.backend.ontology.property;

import com.example.backend.ontology.statement.Statement;
import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "properties")
@NoArgsConstructor
@AllArgsConstructor
public class Property {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;

    private String value;

    @ManyToOne
    @JoinColumn(name = "statement_id")
    private Statement statement;
}
