package com.example.backend.ontology.property;

import com.example.backend.ontology.namespace.NameSpace;
import com.example.backend.ontology.resource.Resource;
import com.example.backend.ontology.statement.StatementService;
import com.example.backend.ontology.verb.Verb;
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

    @Autowired
    private StatementService statementService;

    public List<Property> getProperties(){
        return propertyRepository.findAll();
    }


    public Property addProperty(String statementId,Property property) {
//        List<Property> allByKey = propertyRepository.findAllByKey(property.getKey());
        List<Property> allByStatementId = propertyRepository.findAllByStatementId(Long.parseLong(statementId));
        boolean isPropertInStatementExist = false;
        //allByStatementId.forEach(System.out::println);
        System.out.println(allByStatementId.get(0).getKey());
        for (Property p : allByStatementId) {
            if (p.getValue().equals(property.getValue())  && p.getKey().equals(property.getKey()) ) {
                isPropertInStatementExist = true;
                break;
            }
        }
        if (isPropertInStatementExist) {
            return null;
        }
        property.setStatement(statementService.getStatementById(statementId));
        return propertyRepository.save(property);
    }


    public PropertyWrapper convert(Property property) {
        if (property != null){
            return PropertyWrapper.builder().id(property.getId()).key(property.getKey()).value(property.getValue()).build();
        }else
            return null;
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

    public Property deleteProperty(String id) {
        Optional<Property> p = propertyRepository.findById(Long.parseLong(id));
        if (p.isEmpty()) {
            return null;
        }
        propertyRepository.delete(p.get());
        return p.get();
    }

    private Property getEntityToUpdate(Property current, Property propertyToUpdate) {
        return Property.builder().id(current.getId()).key(propertyToUpdate.getKey()).value(propertyToUpdate.getValue())
                .statement(current.getStatement()).build();

    }

     public Property updateProperty(String id, Property propertyToUpdate) {
        Optional<Property> p = propertyRepository.findById(Long.parseLong(id));
        if (p.isPresent() ){
            return propertyRepository.save(getEntityToUpdate(p.get(),propertyToUpdate));
        }
        return null;
    }

}
