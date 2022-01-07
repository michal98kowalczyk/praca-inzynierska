package com.example.backend.ontology.category;

import com.example.backend.ontology.resource.Resource;
import com.example.backend.ontology.resource.ResourceService;
import com.example.backend.ontology.wrapper.CategoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CategoryService {

    private static final String SOURCE_CATEGORY = "Źródło";
    @Autowired
    private CategoryRepository categoryRepository;

    @Autowired
    private ResourceService resourceService;

    public List<Category> getCategories() {

        return categoryRepository.findAll()
                .stream()
                .filter(category -> !category.getName().equals(SOURCE_CATEGORY))
                .collect(Collectors.toList());
    }

    public Category getCategory(String name) {
        Optional<Category> category = categoryRepository.findByName(name);
        if (category.isEmpty()) {
            return null;
        }

        return category.get();
    }

    public Category addCategory(Category category) {
        Optional<Category> categoryFromDB = categoryRepository.findByName(category.getName());
        if (categoryFromDB.isPresent()) {
            return null;
        }

        return categoryRepository.save(category);
    }

    public Category getExistCategoryByName(String name){
        return categoryRepository.findByName(name).get();
    }

    public Category deleteCategory(String id) {
        Optional<Category> category = categoryRepository.findById(Long.parseLong(id));
        if (category.isEmpty()) {
            return null;
        }
        List<Resource> resourcesByCategoryId = resourceService.getResourcesByCategoryId(Long.parseLong(id));
        if (!resourcesByCategoryId.isEmpty())return null;

        categoryRepository.delete(category.get());
        return category.get();
    }

    public CategoryWrapper convert(Category category) {
        if (category != null){
            return CategoryWrapper.builder().id(category.getId()).name(category.getName()).build();
        }else {
            return null;
        }


    }

    public Category updateCategory(String id, Category categoryToUpdate) {
        Optional<Category> nsCurrent = categoryRepository.findById(Long.parseLong(id));
        Optional<Category> byName = categoryRepository.findByName(categoryToUpdate.getName());
        if (nsCurrent.isPresent() && byName.isEmpty()){
            return categoryRepository.save(getEntityToUpdate(nsCurrent.get(), categoryToUpdate));
        }
        return null;
    }

    private Category getEntityToUpdate(Category current, Category categoryToUpdate) {
        return Category.builder().id(current.getId()).name(categoryToUpdate.getName()).resources(current.getResources()).build();

    }
}
