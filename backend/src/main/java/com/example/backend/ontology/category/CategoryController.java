package com.example.backend.ontology.category;

import com.example.backend.ontology.wrapper.CategoryWrapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api")
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    @GetMapping("category")
    public ResponseEntity getCategories() {

        List<Category> categories = categoryService.getCategories();
        if (categories.isEmpty())return ResponseEntity.notFound().build();

        List<CategoryWrapper> categoryWrapperList = new ArrayList<>();
        categories.forEach(category -> categoryWrapperList.add(categoryService.convert(category)));
        return ResponseEntity.ok(categoryWrapperList);
    }

    @GetMapping("category/{name}")
    public ResponseEntity getCategory(@PathVariable("name") String name) {
        Category ns = categoryService.getCategory(name);
        if (ns == null)return ResponseEntity.notFound().build();

        return ResponseEntity.ok(categoryService.convert(ns));
    }

    @PostMapping("category/add")
    public ResponseEntity<CategoryWrapper> addCategory(@RequestBody Category category){
        Category ns = categoryService.addCategory(category);
        if (ns == null){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(categoryService.convert(ns));
    }

    @DeleteMapping("category/{id}")
    public ResponseEntity<CategoryWrapper> deleteCategory(@PathVariable("id") String id) {
        Category ns = categoryService.deleteCategory(id);
        if (ns == null){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(categoryService.convert(ns));
    }

    @PutMapping("category/{id}")
    public ResponseEntity<CategoryWrapper> updateCategory(@PathVariable("id") String id, @RequestBody Category categoryToUpdate) {
        Category ns = categoryService.updateCategory(id, categoryToUpdate);
        if (ns == null){
            return ResponseEntity.unprocessableEntity().build();
        }
        return ResponseEntity.ok(categoryService.convert(ns));
    }
}
