package com.example.backend.ontology.resource;

import com.example.backend.ontology.category.Category;
import com.example.backend.ontology.category.CategoryService;
import com.example.backend.ontology.resourceproperty.ResourceProperty;
import com.example.backend.ontology.resourceproperty.ResourcePropertyService;
import com.example.backend.ontology.statement.Statement;
import com.example.backend.ontology.statement.StatementService;
import com.example.backend.ontology.wrapper.ResourceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ResourceService {

    @Autowired
    private ResourceRepository resourceRepository;

    @Autowired
    private CategoryService categoryService;

    @Autowired
    private StatementService statementService;

    @Autowired
    private ResourcePropertyService resourcePropertyService;

    private static final String SOURCE_CATEGORY = "Źródło";

    public List<Resource> getSources(){

        List<Resource> all = resourceRepository.findAll()
                .stream()
                .filter(resource -> resource.getCategory().getName().equals(SOURCE_CATEGORY))
                .collect(Collectors.toList());
        return all;
    }

    public List<Resource> getResources(){

        List<Resource> all = resourceRepository.findAll()
                .stream()
                .filter(resource -> !resource.getCategory().getName().equals(SOURCE_CATEGORY))
                .collect(Collectors.toList());
        return all;
    }

    public List<Resource> getResourcesByCategoryId(Long id){
        return resourceRepository.findAllByCategoryId(id);
    }

    public Resource getResource(String name) {
        Optional<Resource> resource = resourceRepository.findByName(name);
        if (resource.isEmpty()) {
            return null;
        }

        return resource.get();
    }

    public Resource getResourceById(String id) {

        Optional<Resource> byId = resourceRepository.findById(Long.parseLong(id));
        if (byId.isPresent()){
            return byId.get();
        }
        return null;
    }



    public Resource addResource(Resource resource) {
        Optional<Resource> resourceFromDb = resourceRepository.findByName(resource.getName());
        if (resourceFromDb.isPresent()) {
            return null;
        }
        Category category = resource.getCategory();
        Category categoryFromDb = categoryService.addCategory(category);
        if(categoryFromDb ==null){
            categoryFromDb = categoryService.getExistCategoryByName(category.getName());
        }
        resource.setCategory(categoryFromDb);
        Resource saved = resourceRepository.save(resource);

        List<ResourceProperty> properties = resource.getProperties();
        if(properties!=null){
            properties.forEach(property -> property.setResource(saved));
            resourcePropertyService.addResourceProperties(properties);
        }

        return saved;
    }

    public Resource getExistResourceByName(String name){
        return resourceRepository.findByName(name).get();
    }

    public ResourceWrapper convert(Resource resource) {
        if (resource != null){
            return ResourceWrapper.builder().id(resource.getId()).name(resource.getName())
                    .category(categoryService.convert(resource.getCategory()))
                    .properties(resourcePropertyService.convert(resourcePropertyService.getByResourceId(resource.getId())))
                    .build();
        }else
            return null;
    }

    public Resource deleteResource(String id) {
        Optional<Resource> res = resourceRepository.findById(Long.parseLong(id));
        if (res.isEmpty()) {
            return null;
        }

        List<Statement> statementsBySubjectId = statementService.getStatementsBySubjectId(Long.parseLong(id));
        List<Statement> statementsByResourceId = statementService.getStatementsByResourceId(Long.parseLong(id));
        List<Statement> statementsBySourceId = statementService.getStatementsBySourceId(Long.parseLong(id));
        if (!statementsByResourceId.isEmpty() || !statementsBySubjectId.isEmpty() || !statementsBySourceId.isEmpty())return null;

        resourcePropertyService.deleteProperties(res.get().getProperties());
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
                .category(current.getCategory())
                .properties(current.getProperties())
                .statements(current.getStatements()).build();

    }
}
