package com.example.backend;

import com.example.backend.ontology.namespace.NameSpace;
import com.example.backend.ontology.namespace.NameSpaceService;
import com.example.backend.ontology.resource.Resource;
import com.example.backend.ontology.resource.ResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DemoData {



    @Autowired
    private NameSpaceService ns;

    @Autowired
    private ResourceService resourceService;

    @EventListener
    public void appReady(ApplicationReadyEvent event){
        NameSpace nameSpace = NameSpace.builder().name("Źródło").build();
        NameSpace space = ns.addNameSpace(nameSpace);

        Resource resource = Resource.builder().name("Automatically prediction").nameSpace(ns.getNameSpace("Źródło")).build();
        resourceService.addResource(resource);


    }
}
