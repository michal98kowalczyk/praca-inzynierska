package com.example.backend.ontology.resource;

import com.example.backend.ontology.namespace.NameSpace;
import com.example.backend.ontology.wrapper.NameSpaceWrapper;
import com.example.backend.ontology.wrapper.ResourceWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class ResourceController {

    @Autowired
    private ResourceService resourceService;

    @GetMapping("resources")
    public ResponseEntity<List<ResourceWrapper>> getResources(){
        List<Resource> resourceList = resourceService.getResources();
        if (resourceList.isEmpty())return ResponseEntity.notFound().build();
        List<ResourceWrapper> resourceWrapperList = new ArrayList<>();
        resourceList.forEach( res -> resourceWrapperList.add(resourceService.convert(res)));
        return ResponseEntity.ok(resourceWrapperList);
    }

    @GetMapping("resource/{name}")
    public ResponseEntity<ResourceWrapper> getResource(@PathVariable("name") String name) {
        Resource res = resourceService.getResource(name);
        if (res == null)return ResponseEntity.notFound().build();

        return ResponseEntity.ok(resourceService.convert(res));
    }

    @PostMapping("resource/add")
    public ResponseEntity<ResourceWrapper> addResource(@RequestBody Resource resource){
        Resource res = resourceService.addResource(resource);
        if (res == null){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(resourceService.convert(res));
    }

    @DeleteMapping("resource/{id}")
    public ResponseEntity<ResourceWrapper> deleteResource(@PathVariable("id") String id) {
        Resource res = resourceService.deleteResource(id);
        if (res == null){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(resourceService.convert(res));
    }

    @PutMapping("resource/{id}")
    public ResponseEntity<ResourceWrapper> updateResource(@PathVariable("id") String id, @RequestBody Resource resourceToUpdate) {
        Resource res = resourceService.updateResource(id, resourceToUpdate );
        if (res == null){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(resourceService.convert(res));
    }
}
