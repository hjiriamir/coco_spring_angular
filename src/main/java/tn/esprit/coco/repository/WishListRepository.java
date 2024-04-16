package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.WishList;

public interface WishListRepository extends JpaRepository<WishList,Long> {
}
