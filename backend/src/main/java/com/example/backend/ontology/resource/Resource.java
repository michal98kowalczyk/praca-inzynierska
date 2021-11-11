package com.example.backend.ontology.resource;


import com.example.backend.ontology.namespace.NameSpace;
import com.example.backend.ontology.statement.Statement;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "resources")
@NoArgsConstructor
@AllArgsConstructor
public class Resource {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToOne
    @JoinColumn(name = "namespace_id")
    private NameSpace nameSpace;

    @OneToMany(mappedBy = "resource")
    private List<Statement> statements;
}
