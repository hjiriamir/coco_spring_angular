package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.coco.entity.CategoryProduct;

@Repository
public interface CategoryProductRepository extends JpaRepository<CategoryProduct,Long> {
   // CategoryProduct findByCategoryName(String categoryName);

}
