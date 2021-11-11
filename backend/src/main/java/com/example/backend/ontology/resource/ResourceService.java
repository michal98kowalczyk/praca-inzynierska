package com.example.backend.ontology.resource;

import com.example.backend.ontology.literal.Literal;
import com.example.backend.ontology.namespace.NameSpace;
import com.example.backend.ontology.namespace.NameSpaceService;
import com.example.backend.ontology.wrapper.ResourceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private NameSpaceService nameSpaceService;

    public Resource addResource(Resource resource) {
        Optional<Resource> resourceFromDb = resourceRepository.findByName(resource.getName());
        if (resourceFromDb.isPresent()) {
            return null;
        }

        NameSpace nameSpace = resource.getNameSpace();
        NameSpace nameSpaceFromDb = nameSpaceService.addNameSpace(nameSpace);
        if(nameSpaceFromDb ==null){
            nameSpaceFromDb = nameSpaceService.getExistNameSpaceByName(nameSpace.getName());
        }
        resource.setNameSpace(nameSpaceFromDb);
        return resourceRepository.save(resource);
    }

    public Resource getExistResourceByName(String name){
        return resourceRepository.findByName(name).get();
    }

    public ResourceWrapper convert(Resource resource) {
        return ResourceWrapper.builder().id(resource.getId()).name(resource.getName())
                .namespace(nameSpaceService.convert(resource.getNameSpace())).build();
    }
}
