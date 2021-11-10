package com.example.backend.ontology.model;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ModelService {

    @Autowired
    private ModelRepository modelRepository;

    public ResponseEntity getModels() {
        return new ResponseEntity<>(modelRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity getModel(String name) {
        Optional<Model> model = modelRepository.findByName(name);
        if (model.isEmpty()) {
            return (ResponseEntity) ResponseEntity.notFound();
        }

        return new ResponseEntity<>(model, HttpStatus.OK);
    }

    public ResponseEntity<Model> addModel(Model model) {
        Optional<Model> userFromDb = modelRepository.findByName(model.getName());
        if (userFromDb.isPresent()) {
            return ResponseEntity.status(HttpStatus.UNPROCESSABLE_ENTITY)
                    .build();
        }

        return new ResponseEntity<>(modelRepository.save(model), HttpStatus.OK);
    }

    public ResponseEntity<Model> updateModel(String id, Model modelToUpdate) {

        Optional<Model> current = modelRepository.findById(Long.parseLong(id));
        return current
                .map(value -> new ResponseEntity<>(modelRepository.save(getEntityToUpdate(value, modelToUpdate)),
                        HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(modelToUpdate, HttpStatus.BAD_REQUEST));
    }


    public ResponseEntity<Model> deleteModel(String id) {
        Optional<Model> model = modelRepository.findById(Long.parseLong(id));
        if (model.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
        modelRepository.delete(model.get());
        return new ResponseEntity<>(model.get(), HttpStatus.OK);
    }

    private Model getEntityToUpdate(Model current, Model modelToUpdate) {
        return Model.builder().id(current.getId()).name(modelToUpdate.getName()).build();
    }
}
