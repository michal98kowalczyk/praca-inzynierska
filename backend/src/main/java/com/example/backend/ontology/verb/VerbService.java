package com.example.backend.ontology.verb;

import com.example.backend.ontology.resource.Resource;
import com.example.backend.ontology.resource.ResourceRepository;
import com.example.backend.ontology.statement.Statement;
import com.example.backend.ontology.statement.StatementService;
import com.example.backend.ontology.wrapper.VerbWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class VerbService {
    @Autowired
    private VerbRepository verbRepository;

    @Autowired
    private StatementService statementService;

    public List<Verb> getVerbs(){
        return verbRepository.findAll();
    }

    public Verb getVerb(String verb) {
        Optional<Verb> v = verbRepository.findByVerb(verb);
        if (v.isEmpty()) {
            return null;
        }

        return v.get();
    }

    public Verb addVerb(Verb verb) {
        Optional<Verb> verbFromDb = verbRepository.findByVerb(verb.getVerb());
        if (verbFromDb.isPresent()) {
            return null;
        }

        return verbRepository.save(verb);
    }

    public Verb getExistVerbByVerb(String verb){
        return verbRepository.findByVerb(verb).get();
    }

    public VerbWrapper convert(Verb verb) {
        if(verb==null)return null;
        return VerbWrapper.builder().id(verb.getId()).verb(verb.getVerb()).build();
    }

    public Verb deleteVerb(String id) {
        Optional<Verb> v = verbRepository.findById(Long.parseLong(id));
        if (v.isEmpty()) {
            return null;
        }
        List<Statement> statementsByVerbId = statementService.getStatementsByPredicateId(Long.parseLong(id));
        if (!statementsByVerbId.isEmpty()) return null;

        verbRepository.delete(v.get());
        return v.get();
    }

    private Verb getEntityToUpdate(Verb current, Verb verbToUpdate) {
        return Verb.builder().id(current.getId()).verb(verbToUpdate.getVerb()).statements(current.getStatements()).build();

    }

    public Verb updateVerb(String id, Verb verbToUpdate) {
        Optional<Verb> v = verbRepository.findById(Long.parseLong(id));
        Optional<Verb> byName = verbRepository.findByVerb(verbToUpdate.getVerb());
        if (v.isPresent() && byName.isEmpty()) {
            return verbRepository.save(getEntityToUpdate(v.get(), verbToUpdate));
        }
        return null;


    }
}
