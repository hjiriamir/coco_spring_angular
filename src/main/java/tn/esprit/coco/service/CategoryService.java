package tn.esprit.coco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.coco.entity.Category;
import tn.esprit.coco.entity.SubCategory;
import tn.esprit.coco.repository.CategoryRepository;
import tn.esprit.coco.repository.SubCategoryRepository;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CategoryService {

    private final CategoryRepository categoryRepository;

    @Autowired
    public CategoryService(CategoryRepository categoryRepository,SubCategoryRepository subCategoryRepository) {

        this.categoryRepository = categoryRepository;
        this.subCategoryRepository = subCategoryRepository;
    }
    private final SubCategoryRepository subCategoryRepository;


    public Category saveCategory(Category category) {
        return categoryRepository.save(category);
    }

    public Optional<Category> getCategoryById(Long id) {
        return categoryRepository.findById(id);
    }

    public List<Category> getAllCategories() {
        return categoryRepository.findAll();
    }

    public void deleteCategoryById(Long id) {
        categoryRepository.deleteById(id);
    }

    public Category updateCategory(Long id, Category updatedCategory) {
        if (categoryRepository.existsById(id)) {
            updatedCategory.setCategoryID(id);
            return categoryRepository.save(updatedCategory);
        } else {
            return null; // Category with given id not found
        }
    }
    public Category addSubCategoryToCategory(Long categoryId, SubCategory subCategory) {
        Optional<Category> optionalCategory = categoryRepository.findById(categoryId);
        if (optionalCategory.isPresent()) {
            Category category = optionalCategory.get();
            subCategory.setCategory(category); // Assign the category to the sub-category

            // Get the Set of sub-categories from the parent category and add the sub-category
            Set<SubCategory> subCategories = category.getSubCategories();
            subCategories.add(subCategory);

            // Save the updated category
            return categoryRepository.save(category);
        } else {
            return null; // Category with given id not found
        }
    }
    public List<SubCategory> getAllSubCategoriesByCategoryId(Long categoryId) {
        return subCategoryRepository.findByCategory_CategoryID(categoryId);
    }

}
