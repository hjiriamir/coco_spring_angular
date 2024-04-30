package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.Category;

public interface CategoryRepository extends JpaRepository<Category,Long> {

}
