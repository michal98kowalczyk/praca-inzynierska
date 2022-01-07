package com.example.backend.ontology.category;

import com.example.backend.ontology.resource.Resource;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import lombok.*;

import javax.persistence.*;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "categories")
@NoArgsConstructor
@AllArgsConstructor
public class Category {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;
    @JsonManagedReference
    @OneToMany(mappedBy = "category")
    private List<Resource> resources;
}
