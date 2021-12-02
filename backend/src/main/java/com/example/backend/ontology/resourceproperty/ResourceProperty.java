package com.example.backend.ontology.resourceproperty;

import com.example.backend.ontology.resource.Resource;
import com.example.backend.ontology.statement.Statement;
import lombok.*;

import javax.persistence.*;
@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "resource_properties")
@NoArgsConstructor
@AllArgsConstructor
public class ResourceProperty {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String key;

    private String value;

    @ManyToOne
    @JoinColumn(name = "resource_id")
    private Resource resource;


}