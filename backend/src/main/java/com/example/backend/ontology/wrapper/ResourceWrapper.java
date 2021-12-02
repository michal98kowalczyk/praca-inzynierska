package com.example.backend.ontology.wrapper;

import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResourceWrapper {
    Long id;
    String name;
    NameSpaceWrapper namespace;
    List<ResourcePropertyWrapper> properties;
}
