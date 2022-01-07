package com.example.backend.ontology.model;

import com.example.backend.ontology.statement.Statement;
import com.example.backend.ontology.wrapper.ModelOutputWrapper;
import com.example.backend.ontology.wrapper.StatementDetailsWrapper;
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
        List<ModelOutputWrapper> modelOutputWrappers = modelService.getModels();

        if (modelOutputWrappers.isEmpty()){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(modelOutputWrappers);
        }
    }

    @GetMapping("statement/{id}/details")
    public ResponseEntity getStatementDetails(@PathVariable("id") String statementId) {

        StatementDetailsWrapper statementDetailsWrapper = modelService.getStatementDetails(statementId);
        if (statementDetailsWrapper == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(statementDetailsWrapper);
        }
    }

    @GetMapping("model/{name}")
    public ResponseEntity getModel(@PathVariable("name") String name) {

        ModelOutputWrapper modelOutputWrapper = modelService.getModel(name);
        if (modelOutputWrapper == null){
            return ResponseEntity.notFound().build();
        }else{
            return ResponseEntity.ok(modelOutputWrapper);
        }
    }


    @PostMapping("model/add")
    public ResponseEntity<ModelOutputWrapper> addModel(@RequestBody Model model) {
        Model created = modelService.addModel(model);
        if(created == null){
            return  ResponseEntity.unprocessableEntity().build();
        }else{
            return ResponseEntity.ok(modelService.getModelOutputWrapper(created));
        }

    }

    @PutMapping("model/{id}")
    public ResponseEntity<ModelOutputWrapper> updateModel(@PathVariable("id") String id, @RequestBody Model modelToUpdate) {
        Model model = modelService.updateModel(id, modelToUpdate);
        if (model == null)return ResponseEntity.notFound().build();

        return ResponseEntity.ok(modelService.getModelOutputWrapper(model));
    }

    @DeleteMapping("model/{id}")
    public ResponseEntity<ModelOutputWrapper> deleteModel(@PathVariable("id") String id) {
        Model model = modelService.deleteModel(id);

        if (model == null)return ResponseEntity.notFound().build();

        return ResponseEntity.ok(modelService.getModelOutputWrapper(model));
    }

}
