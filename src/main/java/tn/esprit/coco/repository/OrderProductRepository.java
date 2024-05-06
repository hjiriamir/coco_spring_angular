package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.OrderProduct;
import tn.esprit.coco.entity.OrderStatus;

public interface OrderProductRepository extends JpaRepository<OrderProduct,Long> {
    OrderProduct findByUser_IdAndOrderStatus(Long id, OrderStatus orderStatus);
}
