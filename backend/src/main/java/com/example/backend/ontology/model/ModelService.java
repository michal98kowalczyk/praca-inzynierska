package com.example.backend.ontology.model;

import com.example.backend.ontology.statement.Statement;
import com.example.backend.ontology.statement.StatementService;
import com.example.backend.ontology.wrapper.ModelOutputWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

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
        model.getStatements().forEach(statement -> statementIds.add(statement.getId()));
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
        return modelRepository.findByName(name).get();
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
}
