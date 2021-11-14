package com.example.backend.ontology.statement;

import com.example.backend.ontology.model.Model;
import com.example.backend.ontology.wrapper.StatementOutputWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("statements/add")
    public ResponseEntity<StatementOutputWrapper> addStatement(@RequestBody Statement statement) {
        Statement res = statementService.addStatement(statement);
        if (res == null){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(statementService.convert(res));
    }
}
