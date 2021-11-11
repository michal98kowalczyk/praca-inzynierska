package com.example.backend.ontology.namespace;

import com.example.backend.ontology.model.ModelService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api")
public class NameSpaceController {

    @Autowired
    private NameSpaceService nameSpaceService;

    @GetMapping("namespace")
    public ResponseEntity getModels() {
        return nameSpaceService.getNameSpaces();
    }

    @GetMapping("namespace/{name}")
    public ResponseEntity getModel(@PathVariable("name") String name) {
        return nameSpaceService.getNameSpace(name);
    }
}
