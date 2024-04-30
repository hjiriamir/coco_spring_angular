package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.OrderProduct;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {
}
