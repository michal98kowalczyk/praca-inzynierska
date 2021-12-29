package com.example.backend;

import com.example.backend.ontology.category.Category;
import com.example.backend.ontology.category.CategoryService;
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
    private CategoryService ns;

    @Autowired
    private ResourceService resourceService;

    @EventListener
    public void appReady(ApplicationReadyEvent event){
        Category category = Category.builder().name(SOURCE_CATEGORY).build();
        Category space = ns.addCategory(category);

        Resource resource = Resource.builder().name(AUTOMATICALLY_PREDICTION).category(ns.getCategory(SOURCE_CATEGORY)).build();
        resourceService.addResource(resource);


    }
}
