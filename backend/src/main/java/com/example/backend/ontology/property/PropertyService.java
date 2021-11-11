package com.example.backend.ontology.property;

import com.example.backend.ontology.namespace.NameSpace;
import com.example.backend.ontology.resource.Resource;
import com.example.backend.ontology.wrapper.PropertyWrapper;
import com.example.backend.ontology.wrapper.ResourceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class PropertyService {

    @Autowired
    private PropertyRepository propertyRepository;

    public Property addProperty(Property property) {

        return propertyRepository.save(property);
    }


    public PropertyWrapper convert(Property property) {
        return PropertyWrapper.builder().id(property.getId()).key(property.getKey()).value(property.getValue()).build();
    }

    public List<PropertyWrapper> convert(List<Property> properties) {
        List<PropertyWrapper> propertyWrappers = new ArrayList<>();
        properties.forEach(property -> propertyWrappers.add(convert(property)));
        return propertyWrappers;
    }

    public List<Property> addProperties(List<Property> properties) {
        return  propertyRepository.saveAll(properties);
    }

    public List<Property> getByStatementId(Long statementId) {
        return  propertyRepository.findAllByStatementId(statementId);
    }
}
