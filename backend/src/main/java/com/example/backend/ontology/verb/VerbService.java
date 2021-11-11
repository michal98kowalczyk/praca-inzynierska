package com.example.backend.ontology.verb;

import com.example.backend.ontology.resource.Resource;
import com.example.backend.ontology.resource.ResourceRepository;
import com.example.backend.ontology.wrapper.VerbWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class VerbService {
    @Autowired
    private VerbRepository verbRepository;

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
}
