package com.example.backend.ontology.statement;

import com.example.backend.ontology.literal.Literal;
import com.example.backend.ontology.model.Model;
import com.example.backend.ontology.property.Property;
import com.example.backend.ontology.resource.Resource;
import com.example.backend.ontology.verb.Verb;
import com.fasterxml.jackson.annotation.JsonBackReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "statements")
@NoArgsConstructor
@AllArgsConstructor
public class Statement {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonBackReference
    @ManyToOne
    private Model model;

    @OneToMany(mappedBy = "statement")
    private List<Property> properties;

    @ManyToOne
    @JoinColumn(name = "objLit")
    private Literal literal;

    boolean isLit;

    @ManyToOne
    @JoinColumn(name = "source")
    private Resource source;

    @ManyToOne
    @JoinColumn(name = "subject")
    private Resource subject;

    @ManyToOne
    @JoinColumn(name = "predicate")
    private Verb predicate;


    @ManyToOne
    @JoinColumn(name = "objRes")
    private Resource resource;

    boolean isRes;

    Double probability;

}
