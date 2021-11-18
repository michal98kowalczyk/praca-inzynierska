package com.example.backend.ontology.resource;

import com.example.backend.ontology.literal.Literal;
import com.example.backend.ontology.namespace.NameSpace;
import com.example.backend.ontology.namespace.NameSpaceService;
import com.example.backend.ontology.wrapper.LiteralWrapper;
import com.example.backend.ontology.wrapper.ResourceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private NameSpaceService nameSpaceService;

    public List<Resource> getResources(){
        return resourceRepository.findAll();
    }

    public Resource getResource(String name) {
        Optional<Resource> resource = resourceRepository.findByName(name);
        if (resource.isEmpty()) {
            return null;
        }

        return resource.get();
    }

    public Resource addResource(Resource resource) {
        Optional<Resource> resourceFromDb = resourceRepository.findByName(resource.getName());
        if (resourceFromDb.isPresent()) {
            return null;
        }
        System.out.println(resource);
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
        if (resource != null){
            return ResourceWrapper.builder().id(resource.getId()).name(resource.getName())
                    .namespace(nameSpaceService.convert(resource.getNameSpace())).build();
        }else
            return null;
    }

    public Resource deleteResource(String id) {
        Optional<Resource> res = resourceRepository.findById(Long.parseLong(id));
        if (res.isEmpty()) {
            return null;
        }
        resourceRepository.delete(res.get());
        return res.get();
    }

    public Resource updateResource(String id, Resource resourceToUpdate) {
        Optional<Resource> resCurrent = resourceRepository.findById(Long.parseLong(id));
        Optional<Resource> byName = resourceRepository.findByName(resourceToUpdate.getName());
        if (resCurrent.isPresent() && byName.isEmpty()){
            return resourceRepository.save(getEntityToUpdate(resCurrent.get(),resourceToUpdate));
        }
        return null;
    }

    private Resource getEntityToUpdate(Resource current, Resource resourceToUpdate) {
        return Resource.builder().id(current.getId()).name(resourceToUpdate.getName())
                .nameSpace(current.getNameSpace()).statements(current.getStatements()).build();

    }
}
