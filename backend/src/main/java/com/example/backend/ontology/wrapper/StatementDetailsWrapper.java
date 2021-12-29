package com.example.backend.ontology.wrapper;


import lombok.*;

@Getter
@Setter
@ToString
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StatementDetailsWrapper {

    int countOfPrediction;
    int countOfPositivePrediction;
    int countOfNegativePrediction;
    double minConfidence;
    double maxConfidence;
    double avgConfidence;
}
