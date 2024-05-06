package tn.esprit.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.entity.Category;
import tn.esprit.coco.entity.SubCategory;
import tn.esprit.coco.service.CategoryService;
import tn.esprit.coco.service.SubCategoryService;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
//@CrossOrigin("*")
@CrossOrigin(origins = "http://localhost:4200",methods={RequestMethod.POST,RequestMethod.PUT, RequestMethod.DELETE,RequestMethod.GET,RequestMethod.PATCH})
@RequestMapping("/api/categories")
public class CategoryController {

    private final CategoryService categoryService;
    private final SubCategoryService subCategoryService;

    @Autowired
    public CategoryController(CategoryService categoryService, SubCategoryService subCategoryService) {

        this.categoryService = categoryService;
        this.subCategoryService = subCategoryService;

    }
    @PostMapping("/{categoryId}/addSubCategory")
    public ResponseEntity<Category> addSubCategoryToCategory(@PathVariable Long categoryId, @RequestBody SubCategory subCategory) {
        Category updatedCategory = categoryService.addSubCategoryToCategory(categoryId, subCategory);
        if (updatedCategory != null) {
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }
    @GetMapping("/getAllCategories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryService.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @GetMapping("getCategoryById/{id}")
    public ResponseEntity<Category> getCategoryById(@PathVariable Long id) {
        Optional<Category> category = categoryService.getCategoryById(id);
        return category.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addCategory")
    public ResponseEntity<Category> addCategory(@RequestBody Category category) {
        Category savedCategory = categoryService.saveCategory(category);
        return new ResponseEntity<>(savedCategory, HttpStatus.CREATED);
    }

    @PutMapping("updateCategory/{id}")
    public ResponseEntity<Category> updateCategory(@PathVariable Long id, @RequestBody Category category) {
        Category updatedCategory = categoryService.updateCategory(id, category);
        if (updatedCategory != null) {
            return new ResponseEntity<>(updatedCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deleteCategory/{id}")
    public ResponseEntity<Void> deleteCategory(@PathVariable Long id) {
        categoryService.deleteCategoryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @GetMapping("{categoryId}/subcategories")
    public ResponseEntity<List<SubCategory>> getAllSubCategoriesByCategory(@PathVariable Long categoryId) {
        Optional<Category> categoryOptional = categoryService.getCategoryById(categoryId);
        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            List<SubCategory> subCategories = category.getSubCategories().stream().collect(Collectors.toList());
            return new ResponseEntity<>(subCategories, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

}
