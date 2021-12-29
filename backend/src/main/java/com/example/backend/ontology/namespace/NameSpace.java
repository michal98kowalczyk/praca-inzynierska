package com.example.backend.ontology.namespace;

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
@Table(name = "namespaces")
@NoArgsConstructor
@AllArgsConstructor
public class NameSpace {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;
    @JsonManagedReference
    @OneToMany(mappedBy = "nameSpace")
    private List<Resource> resources;
}
