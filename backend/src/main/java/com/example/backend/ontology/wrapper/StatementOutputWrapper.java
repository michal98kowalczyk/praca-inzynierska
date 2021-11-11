package com.example.backend.ontology.wrapper;

import com.example.backend.ontology.literal.Literal;
import com.example.backend.ontology.model.Model;
import com.example.backend.ontology.resource.Resource;
import com.example.backend.ontology.verb.Verb;
import lombok.*;

import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatementOutputWrapper {

    private Long id;

    ResourceWrapper subject;

    VerbWrapper predicate;

    ResourceWrapper resource;

    LiteralWrapper literal;

    Boolean isRes;

    Boolean isLit;

    Long modelId;

    Double probablity;

    List<PropertyWrapper> properties;



}
