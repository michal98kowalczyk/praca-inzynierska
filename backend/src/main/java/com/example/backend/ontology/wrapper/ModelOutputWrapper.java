package com.example.backend.ontology.wrapper;

import com.example.backend.ontology.statement.Statement;
import lombok.*;

import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.List;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ModelOutputWrapper {

    private Long id;


    private String name;


    private List<Long> statements;
}
