package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.coco.entity.SubCategoryProduct;

@Repository
public interface SubCategoryProductRepository extends JpaRepository<SubCategoryProduct,Long> {
}
