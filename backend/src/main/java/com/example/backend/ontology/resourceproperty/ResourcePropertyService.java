package com.example.backend.ontology.resourceproperty;

import com.example.backend.ontology.property.Property;
import com.example.backend.ontology.resource.ResourceService;
import com.example.backend.ontology.wrapper.ResourcePropertyWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class ResourcePropertyService {

    @Autowired
    private ResourcePropertyRepository resourcePropertyRepository;

    @Autowired
    private ResourceService resourceService;

    public List<ResourceProperty> getResourceProperties(){
        return resourcePropertyRepository.findAll();
    }

    public ResourceProperty addResourceProperty(String resourceId, ResourceProperty property){
        property.setResource(resourceService.getResourceById(resourceId));
        return resourcePropertyRepository.save(property);
    }

    public List<ResourceProperty> addResourceProperties(List<ResourceProperty> properties) {
        return  resourcePropertyRepository.saveAll(properties);
    }

    public List<ResourceProperty> getByResourceId(Long resourceId) {
        return  resourcePropertyRepository.findAllByResourceId(resourceId);
    }

    public ResourcePropertyWrapper convert(ResourceProperty property) {
        if (property != null){
            return ResourcePropertyWrapper.builder().id(property.getId()).key(property.getKey()).value(property.getValue()).build();
        }else
            return null;
    }

    public List<ResourcePropertyWrapper> convert(List<ResourceProperty> properties) {
        List<ResourcePropertyWrapper> propertyWrappers = new ArrayList<>();
        properties.forEach(property -> propertyWrappers.add(convert(property)));
        return propertyWrappers;
    }

    public ResourceProperty deleteResourceProperty(String id) {
        Optional<ResourceProperty> p = resourcePropertyRepository.findById(Long.parseLong(id));
        if (p.isEmpty()) {
            return null;
        }
        resourcePropertyRepository.delete(p.get());
        return p.get();
    }

    public List<ResourceProperty> deleteProperties(List<ResourceProperty> properties) {
        List<ResourceProperty> res = new ArrayList<>();
        properties.forEach(p -> res.add(deleteResourceProperty(p.getId().toString())));
        return res;
    }
/*







    public List<Property> addProperties(List<Property> properties) {
        return  propertyRepository.saveAll(properties);
    }

    public List<Property> getByStatementId(Long statementId) {
        return  propertyRepository.findAllByStatementId(statementId);
    }



 */
}
