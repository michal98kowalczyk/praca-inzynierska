package com.example.backend.ontology.model;

import com.example.backend.ontology.statement.Statement;
import com.example.backend.ontology.statement.StatementService;
import com.example.backend.ontology.wrapper.ModelOutputWrapper;
import com.example.backend.ontology.wrapper.StatementDetailsWrapper;
import com.example.backend.ontology.wrapper.StatementOutputWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ModelService {

    @Autowired
    private ModelRepository modelRepository;

    @Autowired
    private StatementService statementService;

    public List<ModelOutputWrapper> getModels() {
        List<ModelOutputWrapper> result = new ArrayList<>();
        List<Model> all = modelRepository.findAll();
        all.forEach(model -> {

            result.add( getModelOutputWrapper(model));
        });

        return result;
    }

    public ModelOutputWrapper getModel(String name) {
        Optional<Model> model = modelRepository.findByName(name);
        if (model.isEmpty())return null;
        return getModelOutputWrapper(model.get());
    }



    public ModelOutputWrapper getModelOutputWrapper(Model model){
        List<Long> statementIds = new ArrayList<>();
        List<Statement> statements = model.getStatements();
        if(statements != null && !statements.isEmpty())
            statements.forEach(statement -> statementIds.add(statement.getId()));
        return new ModelOutputWrapper().builder().id(model.getId()).name(model.getName()).statements(statementIds).build();

    }


    public Model addModel(Model model) {
        Optional<Model> modelFromDb = modelRepository.findByName(model.getName());
        if (modelFromDb.isPresent()) {
            return null;
        }

        return modelRepository.save(model);
    }

    public Model getExistModelByName(String name){

        Optional<Model> byName = modelRepository.findByName(name);
        if (byName.isEmpty())return null;

        return byName.get();
    }


    public Model updateModel(String id, Model modelToUpdate) {

        Optional<Model> current = modelRepository.findById(Long.parseLong(id));
        if (current.isPresent())return current.get();

        return null;
    }


    public Model deleteModel(String id) {
        Optional<Model> model = modelRepository.findById(Long.parseLong(id));
        if (model.isEmpty()) {
            return null;
        }
        List<Statement> statements = model.get().getStatements();
        statementService.deleteStatements(statements);

        modelRepository.delete(model.get());
        return model.get();
    }

    private Model getEntityToUpdate(Model current, Model modelToUpdate) {
        return Model.builder().id(current.getId()).name(modelToUpdate.getName()).statements(current.getStatements()).build();

    }

    public StatementDetailsWrapper getStatementDetails(String statementId) {

        List<Statement> lstStatements = statementService.getStatementsForDetails(statementId);
        if(lstStatements==null || lstStatements.isEmpty())return null;

        StatementDetailsWrapper result = new StatementDetailsWrapper();
        result.setCountOfPrediction(lstStatements.size());
        result.setCountOfPositivePrediction(lstStatements.stream().filter(s -> s.getProbability()>=0).collect(Collectors.toList()).size());
        result.setCountOfNegativePrediction(lstStatements.stream().filter(s -> s.getProbability()<0).collect(Collectors.toList()).size());
        List<Double> probabilities = lstStatements.stream().map(s -> s.getProbability()).collect(Collectors.toList());
        System.out.println(probabilities);
        result.setMinProbability(Collections.min(probabilities));
        result.setMaxProbabilility(Collections.max(probabilities));
        Double sum = probabilities.stream().reduce(0.0,Double::sum);
        Double avg = sum/probabilities.size();
        BigDecimal bd = new BigDecimal(avg).setScale(2, RoundingMode.HALF_UP);
        double newInput = bd.doubleValue();
        result.setAvgProbabilility(newInput);

        return result;
    }
}
