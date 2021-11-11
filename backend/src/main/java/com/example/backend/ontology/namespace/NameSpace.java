package com.example.backend.ontology.namespace;

import com.example.backend.ontology.resource.Resource;
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

    @OneToMany(mappedBy = "nameSpace")
    private List<Resource> resources;
}
