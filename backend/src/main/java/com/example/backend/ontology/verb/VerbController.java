package com.example.backend.ontology.verb;

import com.example.backend.ontology.wrapper.VerbWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class VerbController {

    @Autowired
    private VerbService verbService;

    @GetMapping("verbs")
    public ResponseEntity<List<VerbWrapper>> getVerbs(){
        List<Verb> verbList = verbService.getVerbs();
        if (verbList.isEmpty())return ResponseEntity.notFound().build();
        List<VerbWrapper> verbWrapperList = new ArrayList<>();
        verbList.forEach( v -> verbWrapperList.add(verbService.convert(v)));
        return ResponseEntity.ok(verbWrapperList);
    }
    @GetMapping("verbs/{verb}")
    public ResponseEntity<VerbWrapper> getVerb(@PathVariable("verb") String verb) {
        Verb v = verbService.getVerb(verb);
        if (v == null)return ResponseEntity.notFound().build();

        return ResponseEntity.ok(verbService.convert(v));
    }

    @PostMapping("verb/add")
    public ResponseEntity<VerbWrapper> addVerb(@RequestBody Verb verb){
        Verb v = verbService.addVerb(verb);
        if (v == null){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(verbService.convert(v));
    }

    @DeleteMapping("verb/{id}")
    public ResponseEntity<VerbWrapper> deleteVerb(@PathVariable("id") String id) {
        Verb v = verbService.deleteVerb(id);
        if (v == null){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(verbService.convert(v));
    }

    @PutMapping("verb/{id}")
    public ResponseEntity<VerbWrapper> updateVerb(@PathVariable("id") String id, @RequestBody Verb verbToUpdate) {
        Verb v = verbService.updateVerb(id, verbToUpdate );
        if (v == null){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(verbService.convert(v));
    }
}
