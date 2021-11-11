package com.example.backend.ontology.wrapper;

import lombok.*;

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
}
