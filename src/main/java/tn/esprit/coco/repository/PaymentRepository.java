package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.Payment;

public interface PaymentRepository extends JpaRepository<Payment,Long> {
}
