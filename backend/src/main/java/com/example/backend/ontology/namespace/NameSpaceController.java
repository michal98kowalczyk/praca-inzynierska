package com.example.backend.ontology.namespace;

import com.example.backend.ontology.model.Model;
import com.example.backend.ontology.model.ModelService;
import com.example.backend.ontology.wrapper.NameSpaceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class NameSpaceController {

    @Autowired
    private NameSpaceService nameSpaceService;

    @GetMapping("namespace")
    public ResponseEntity getNamespaces() {

        List<NameSpace> nameSpaces = nameSpaceService.getNameSpaces();
        if (nameSpaces.isEmpty())return ResponseEntity.notFound().build();

        List<NameSpaceWrapper> nameSpaceWrapperList = new ArrayList<>();
        nameSpaces.forEach(nameSpace -> nameSpaceWrapperList.add(nameSpaceService.convert(nameSpace)));
        return ResponseEntity.ok(nameSpaceWrapperList);
    }

    @GetMapping("namespace/{name}")
    public ResponseEntity getNamespace(@PathVariable("name") String name) {
        NameSpace ns = nameSpaceService.getNameSpace(name);
        if (ns == null)return ResponseEntity.notFound().build();

        return ResponseEntity.ok(nameSpaceService.convert(ns));
    }

    @PostMapping("namespace/add")
    public ResponseEntity<NameSpaceWrapper> addNamespace(@RequestBody NameSpace nameSpace){
        System.out.println(nameSpace);
        NameSpace ns = nameSpaceService.addNameSpace(nameSpace);
        if (ns == null){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(nameSpaceService.convert(ns));
    }

    @DeleteMapping("namespace/{id}")
    public ResponseEntity<NameSpaceWrapper> deleteNameSpace(@PathVariable("id") String id) {
        NameSpace ns = nameSpaceService.deleteNameSpace(id);
        if (ns == null){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(nameSpaceService.convert(ns));
    }

    @PutMapping("namespace/{id}")
    public ResponseEntity<NameSpaceWrapper> updateNamespace(@PathVariable("id") String id, @RequestBody NameSpace nameSpaceToUpdate) {
        NameSpace ns = nameSpaceService.updateNameSpace(id, nameSpaceToUpdate );
        if (ns == null){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(nameSpaceService.convert(ns));
    }
}
