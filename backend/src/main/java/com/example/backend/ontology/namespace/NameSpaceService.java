package com.example.backend.ontology.namespace;

import com.example.backend.ontology.wrapper.NameSpaceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class NameSpaceService {

    @Autowired
    private NameSpaceRepository nameSpaceRepository;

    public ResponseEntity getNameSpaces() {
        return new ResponseEntity<>(nameSpaceRepository.findAll(), HttpStatus.OK);
    }

    public ResponseEntity getNameSpace(String name) {
        Optional<NameSpace> nameSpace = nameSpaceRepository.findByName(name);
        if (nameSpace.isEmpty()) {
            return (ResponseEntity) ResponseEntity.notFound();
        }

        return new ResponseEntity<>(nameSpace, HttpStatus.OK);
    }

    public NameSpace addNameSpace(NameSpace nameSpace) {
        Optional<NameSpace> nameSpaceFromDb = nameSpaceRepository.findByName(nameSpace.getName());
        if (nameSpaceFromDb.isPresent()) {
            return null;
        }

        return nameSpaceRepository.save(nameSpace);
    }

    public NameSpace getExistNameSpaceByName(String name){
        return nameSpaceRepository.findByName(name).get();
    }

    public ResponseEntity<NameSpace> deleteNameSpace(String id) {
        Optional<NameSpace> nameSpace = nameSpaceRepository.findById(Long.parseLong(id));
        if (nameSpace.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .build();
        }
        nameSpaceRepository.delete(nameSpace.get());
        return new ResponseEntity<>(nameSpace.get(), HttpStatus.OK);
    }

    public NameSpaceWrapper convert(NameSpace nameSpace) {
        if (nameSpace != null){
            return NameSpaceWrapper.builder().id(nameSpace.getId()).name(nameSpace.getName()).build();
        }else {
            return null;
        }


    }
}
