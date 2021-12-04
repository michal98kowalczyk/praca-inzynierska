package com.example.backend;

import com.example.backend.ontology.namespace.NameSpace;
import com.example.backend.ontology.namespace.NameSpaceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

@Component
public class DemoData {



    @Autowired
    private NameSpaceService ns;

    @EventListener
    public void appReady(ApplicationReadyEvent event){
        NameSpace nameSpace = NameSpace.builder().name("Źródło").build();
        ns.addNameSpace(nameSpace);
    }
}
