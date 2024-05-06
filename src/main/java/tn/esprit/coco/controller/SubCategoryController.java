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

@RestController
//@CrossOrigin("*")
@CrossOrigin(origins = "http://localhost:4200",methods={RequestMethod.POST,RequestMethod.PUT, RequestMethod.DELETE,RequestMethod.GET,RequestMethod.PATCH})
@RequestMapping("/api/subcategories")
public class SubCategoryController {

    private final SubCategoryService subCategoryService;
    private final CategoryService categoryService;

    @Autowired
    public SubCategoryController(SubCategoryService subCategoryService, CategoryService categoryService) {
        this.subCategoryService = subCategoryService;
        this.categoryService = categoryService;
    }

    @GetMapping("/getAllSubCategories")
    public ResponseEntity<List<SubCategory>> getAllSubCategories() {
        List<SubCategory> subCategories = subCategoryService.getAllSubCategories();
        return new ResponseEntity<>(subCategories, HttpStatus.OK);
    }

    @GetMapping("getSubCategoryById/{id}")
    public ResponseEntity<SubCategory> getSubCategoryById(@PathVariable Long id) {
        Optional<SubCategory> subCategory = subCategoryService.getSubCategoryById(id);
        return subCategory.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    //hedy yserelha l 'ajout mel category deja mriglaaa
    @PostMapping("/addSubCategory")
    public ResponseEntity<SubCategory> addSubCategory(@RequestBody SubCategory subCategory) {

        Long categoryId = subCategory.getCategory().getCategoryID();


        Optional<Category> categoryOptional = categoryService.getCategoryById(categoryId);

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();


            subCategory.setCategory(category);


            SubCategory savedSubCategory = subCategoryService.saveSubCategory(subCategory);
            return new ResponseEntity<>(savedSubCategory, HttpStatus.CREATED);
        } else {

            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PutMapping("updateSubCategory/{id}")
    public ResponseEntity<SubCategory> updateSubCategory(@PathVariable Long id, @RequestBody SubCategory subCategory) {
        SubCategory updatedSubCategory = subCategoryService.updateSubCategory(id, subCategory);
        if (updatedSubCategory != null) {
            return new ResponseEntity<>(updatedSubCategory, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deleteSubCategory/{id}")
    public ResponseEntity<Void> deleteSubCategory(@PathVariable Long id) {
        subCategoryService.deleteSubCategoryById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}
