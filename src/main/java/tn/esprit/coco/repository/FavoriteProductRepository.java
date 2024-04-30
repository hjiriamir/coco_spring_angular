package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.coco.entity.FavoriteProduct;
import tn.esprit.coco.entity.Product;
import tn.esprit.coco.entity.User;

import java.util.List;

@Repository

public interface FavoriteProductRepository extends JpaRepository <FavoriteProduct,Long> {
    List<FavoriteProduct> findByUser(User user);
    FavoriteProduct findByUserAndProducts(User user, Product product); }
