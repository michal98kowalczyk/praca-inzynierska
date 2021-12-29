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


    private static final String SOURCE_CATEGORY = "Źródło";
    private static final String AUTOMATICALLY_PREDICTION = "Automatically prediction";
    @Autowired
    private NameSpaceService ns;

    @Autowired
    private ResourceService resourceService;

    @EventListener
    public void appReady(ApplicationReadyEvent event){
        NameSpace nameSpace = NameSpace.builder().name(SOURCE_CATEGORY).build();
        NameSpace space = ns.addNameSpace(nameSpace);

        Resource resource = Resource.builder().name(AUTOMATICALLY_PREDICTION).nameSpace(ns.getNameSpace(SOURCE_CATEGORY)).build();
        resourceService.addResource(resource);


    }
}
