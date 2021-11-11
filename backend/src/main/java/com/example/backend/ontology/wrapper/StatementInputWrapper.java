package com.example.backend.ontology.wrapper;

import com.example.backend.ontology.literal.Literal;
import com.example.backend.ontology.model.Model;
import com.example.backend.ontology.resource.Resource;
import com.example.backend.ontology.verb.Verb;
import lombok.*;

@Getter
@Setter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StatementInputWrapper {

    Resource subject;

    Verb predicate;

    Resource objRes;

    Literal objLit;

    Model model;

}
