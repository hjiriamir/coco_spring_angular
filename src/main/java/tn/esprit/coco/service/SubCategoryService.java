package tn.esprit.coco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.coco.entity.SubCategory;
import tn.esprit.coco.repository.SubCategoryRepository;

import java.util.List;
import java.util.Optional;

@Service
public class SubCategoryService {

    private final SubCategoryRepository subCategoryRepository;

    @Autowired
    public SubCategoryService(SubCategoryRepository subCategoryRepository) {
        this.subCategoryRepository = subCategoryRepository;
    }

    public SubCategory saveSubCategory(SubCategory subCategory) {
        return subCategoryRepository.save(subCategory);
    }

    public Optional<SubCategory> getSubCategoryById(Long id) {
        return subCategoryRepository.findById(id);
    }

    public List<SubCategory> getAllSubCategories() {
        return subCategoryRepository.findAll();
    }

    public void deleteSubCategoryById(Long id) {
        subCategoryRepository.deleteById(id);
    }

    public SubCategory updateSubCategory(Long id, SubCategory updatedSubCategory) {
        if (subCategoryRepository.existsById(id)) {
            updatedSubCategory.setSubCategoryID(id);
            return subCategoryRepository.save(updatedSubCategory);
        } else {
            return null; // SubCategory with given id not found
        }
    }
}
