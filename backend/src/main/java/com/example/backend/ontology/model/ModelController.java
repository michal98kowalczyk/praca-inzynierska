package com.example.backend.ontology.model;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class ModelController {


    @Autowired
    private ModelService modelService;

    @GetMapping("models")
    public ResponseEntity getModels() {
        return modelService.getModels();
    }

    @GetMapping("model/{name}")
    public ResponseEntity getModel(@PathVariable("name") String name) {
        return modelService.getModel(name);
    }


    @PostMapping("model/add")
    public ResponseEntity<Model> addModel(@RequestBody Model model) {
        return modelService.addModel(model);
    }

    @PutMapping("model/{id}")
    public ResponseEntity<Model> updateModel(@PathVariable("id") String id, @RequestBody Model modelToUpdate) {
        return modelService.updateModel(id, modelToUpdate);
    }

    @DeleteMapping("model/{id}")
    public ResponseEntity<Model> deleteModel(@PathVariable("id") String id) {
        return modelService.deleteModel(id);
    }

}
