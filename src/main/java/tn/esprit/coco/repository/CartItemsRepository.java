package tn.esprit.coco.repository;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.coco.entity.CartItems;

import java.util.Optional;

@Repository
public interface CartItemsRepository extends JpaRepository<CartItems, Long> {
    Optional<CartItems> findByProduct_IdProductAndOrder_IdOrderAndUser_Id(Long idProduct, Long idOrder, Long idUser);
}

