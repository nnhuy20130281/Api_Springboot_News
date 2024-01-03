package vn.edu.hcmuaf.apiNews.controller;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import vn.edu.hcmuaf.apiNews.entity.Category;
import vn.edu.hcmuaf.apiNews.service.CategoryService;

import java.util.List;

@RestController
@RequestMapping("/api/cate")
@Transactional
public class CategoryController {

    @Autowired
    private CategoryService categoryService;

    // get all category
    @GetMapping
    public List<Category> getAllCategory() {
        return categoryService.getAllCategory();
    }

    // get all category
    @GetMapping("/all")
    public List<Category> getCategoryAll() {
        return categoryService.getCategoryAll();
    }

    // get all category hidden
    @GetMapping("/hidden")
    public List<Category> getAllCategoryHidden() {
        return categoryService.getAllCategoryHidden();
    }

    // get category by id
    @GetMapping("/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable long id) {
        Category category = categoryService.getCategoryById(id);
        return category != null ? ResponseEntity.ok(category) : ResponseEntity.notFound().build();
    }
    // get news by category
    @PostMapping
    public ResponseEntity<Category> createCategory(@RequestBody Category category) {
        Category createdCategory = categoryService.createCategory(category);
        return new ResponseEntity<>(createdCategory, HttpStatus.CREATED);
    }

    // update category
    @PutMapping("/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable long id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        return updatedCategory != null ? ResponseEntity.ok(updatedCategory) : ResponseEntity.notFound().build();
    }

    // hide category
    @PutMapping("/hidden/{id}")
    public ResponseEntity<Category> hideCategory(@PathVariable long id) {
        categoryService.hideCategory(id);
        return ResponseEntity.noContent().build();
    }

    // delete category
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable long id) {
        categoryService.deleteCategory(id);
        return ResponseEntity.noContent().build();
    }
}
