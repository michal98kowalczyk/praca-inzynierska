package com.example.backend.ontology.model;

import lombok.*;

import javax.persistence.*;

@Getter
@Setter
@ToString
@Builder
@Entity
@Table(name = "models")
@NoArgsConstructor
@AllArgsConstructor
public class Model {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String name;
}
