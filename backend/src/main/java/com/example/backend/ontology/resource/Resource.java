package com.example.backend.ontology.resource;


import com.example.backend.ontology.category.Category;
import com.example.backend.ontology.resourceproperty.ResourceProperty;
import com.example.backend.ontology.statement.Statement;
import com.fasterxml.jackson.annotation.JsonBackReference;
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
    @JsonBackReference
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "resource")
    private List<Statement> statements;

    @OneToMany(mappedBy = "resource")
    private List<ResourceProperty> properties;
}
