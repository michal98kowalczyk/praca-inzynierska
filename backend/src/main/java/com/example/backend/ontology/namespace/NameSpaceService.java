package com.example.backend.ontology.namespace;

import com.example.backend.ontology.model.Model;
import com.example.backend.ontology.resource.Resource;
import com.example.backend.ontology.resource.ResourceService;
import com.example.backend.ontology.wrapper.NameSpaceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class NameSpaceService {

    @Autowired
    private NameSpaceRepository nameSpaceRepository;

    @Autowired
    private ResourceService resourceService;

    public List<NameSpace> getNameSpaces() {
        return nameSpaceRepository.findAll();
    }

    public NameSpace getNameSpace(String name) {
        Optional<NameSpace> nameSpace = nameSpaceRepository.findByName(name);
        if (nameSpace.isEmpty()) {
            return null;
        }

        return nameSpace.get();
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

    public NameSpace deleteNameSpace(String id) {
        Optional<NameSpace> nameSpace = nameSpaceRepository.findById(Long.parseLong(id));
        if (nameSpace.isEmpty()) {
            return null;
        }
        List<Resource> resourcesByNameSpaceId = resourceService.getResourcesByNameSpaceId(Long.parseLong(id));
        if (!resourcesByNameSpaceId.isEmpty())return null;

        nameSpaceRepository.delete(nameSpace.get());
        return nameSpace.get();
    }

    public NameSpaceWrapper convert(NameSpace nameSpace) {
        if (nameSpace != null){
            return NameSpaceWrapper.builder().id(nameSpace.getId()).name(nameSpace.getName()).build();
        }else {
            return null;
        }


    }

    public NameSpace updateNameSpace(String id, NameSpace nameSpaceToUpdate) {
        Optional<NameSpace> nsCurrent = nameSpaceRepository.findById(Long.parseLong(id));
        Optional<NameSpace> byName = nameSpaceRepository.findByName(nameSpaceToUpdate.getName());
        if (nsCurrent.isPresent() && byName.isEmpty()){
            return nameSpaceRepository.save(getEntityToUpdate(nsCurrent.get(),nameSpaceToUpdate));
        }
        return null;
    }

    private NameSpace getEntityToUpdate(NameSpace current, NameSpace nameSpaceToUpdate) {
        return NameSpace.builder().id(current.getId()).name(nameSpaceToUpdate.getName()).resources(current.getResources()).build();

    }
}
