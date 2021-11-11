package com.example.backend.ontology.wrapper;

import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PropertyWrapper {
    Long id;
    String key;
    String value;
}
