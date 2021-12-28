package com.example.backend.ontology.property;

import com.example.backend.ontology.wrapper.PropertyWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("api")
public class PropertyController {

    @Autowired
    private PropertyService propertyService;

    @GetMapping("properties")
    public ResponseEntity<List<PropertyWrapper>> getProperties(){
        List<Property> propertyList = propertyService.getProperties();
        if (propertyList.isEmpty())return ResponseEntity.notFound().build();
        List<PropertyWrapper> propertyWrapperList = new ArrayList<>();
        propertyList.forEach( p -> propertyWrapperList.add(propertyService.convert(p)));
        return ResponseEntity.ok(propertyWrapperList);
    }

    @GetMapping("properties/{statementId}")
    public ResponseEntity<List<PropertyWrapper>> getPropertiesByStatementId(@PathVariable("statementId") String statementId){
        List<Property> propertyList = propertyService.getByStatementId(Long.parseLong(statementId));
        if (propertyList.isEmpty())return ResponseEntity.notFound().build();
        List<PropertyWrapper> propertyWrapperList = new ArrayList<>();
        propertyList.forEach( p -> propertyWrapperList.add(propertyService.convert(p)));
        return ResponseEntity.ok(propertyWrapperList);
    }

    @PostMapping("statement/{id}/property/add")
    public ResponseEntity<PropertyWrapper> addProperty(@PathVariable("id") String statementId, @RequestBody Property property){

        Property p = propertyService.addProperty(statementId,property);
        if (p == null){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(propertyService.convert(p));
    }

    @DeleteMapping("property/{id}")
    public ResponseEntity<PropertyWrapper> deleteVerb(@PathVariable("id") String id) {
        Property p = propertyService.deleteProperty(id);
        if (p == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(propertyService.convert(p));
    }


    @PutMapping("property/{id}")
    public ResponseEntity<PropertyWrapper> updateVerb(@PathVariable("id") String id, @RequestBody Property propertyToUpdate) {
        Property p = propertyService.updateProperty(id, propertyToUpdate);
        if (p == null){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(propertyService.convert(p));
    }

}
