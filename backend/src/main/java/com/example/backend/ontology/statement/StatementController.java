package com.example.backend.ontology.statement;

import com.example.backend.ontology.model.Model;
import com.example.backend.ontology.resource.Resource;
import com.example.backend.ontology.wrapper.ResourceWrapper;
import com.example.backend.ontology.wrapper.StatementOutputWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class StatementController {

    @Autowired
    private StatementService statementService;

    @GetMapping("statements")
    public ResponseEntity getStatements() {
        List<Statement> statements = statementService.getStatements();
        if (statements.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            List<StatementOutputWrapper> statementOutputWrappers = new ArrayList<>();
            statements.forEach(statement -> statementOutputWrappers.add(statementService.convert(statement)));
            return ResponseEntity.ok(statementOutputWrappers);
        }
    }

    @GetMapping("model/{id}/statements")
    public ResponseEntity getStatementsByModelId(@PathVariable Long id ) {
        List<Statement> statements = statementService.getStatementsByModelId(id);
        if (statements.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            List<StatementOutputWrapper> statementOutputWrappers = new ArrayList<>();
            statements.forEach(statement -> statementOutputWrappers.add(statementService.convert(statement)));
            return ResponseEntity.ok(statementOutputWrappers);
        }
    }

    @GetMapping("statement/{modelId}/{subject}/{predicate}/{object}")
    public ResponseEntity getFilteredStatement(@PathVariable("modelId") String modelId,@PathVariable("subject") String subject, @PathVariable("predicate") String predicate, @PathVariable("object") String object) {
        System.out.println("subject "+subject);
        System.out.println("predicate "+predicate);
        System.out.println("object "+object);

        List<Statement> statements = statementService.getFilteredStatements(modelId,subject,predicate,object);
        if (statements.isEmpty()){
            return ResponseEntity.noContent().build();
        }else{
            List<StatementOutputWrapper> statementOutputWrappers = new ArrayList<>();
            statements.forEach(statement -> statementOutputWrappers.add(statementService.convert(statement)));
            return ResponseEntity.ok(statementOutputWrappers);
        }


    }

    @PostMapping("statement/add")
    public ResponseEntity<StatementOutputWrapper> addStatement(@RequestBody Statement statement) {
        Statement res = statementService.addStatement(statement);
        if (res == null){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(statementService.convert(res));
    }

    @PostMapping("statements/add")
    public ResponseEntity<List<StatementOutputWrapper>> addStatement(@RequestBody List<Statement> statements) {
        List<Statement> s = statementService.addStatements(statements);
        if (s == null){
            return ResponseEntity.unprocessableEntity().build();
        }
        List<StatementOutputWrapper> lstSOW = new ArrayList<>();
        s.forEach(st -> lstSOW.add(statementService.convert(st)));
        return ResponseEntity.ok(lstSOW);
    }

    @DeleteMapping("statement/{id}")
    public ResponseEntity<StatementOutputWrapper> deleteStatement(@PathVariable("id") String id) {
        Statement s = statementService.deleteStatement(id);
        if (s == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(statementService.convert(s));
    }

    @PutMapping("statement/{id}")
    public ResponseEntity<StatementOutputWrapper> updateStatement(@PathVariable("id") String id, @RequestBody Statement statementToUpdate) {
        Statement s = statementService.updateStatement(id, statementToUpdate );
        if (s == null){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(statementService.convert(s));
    }
}
