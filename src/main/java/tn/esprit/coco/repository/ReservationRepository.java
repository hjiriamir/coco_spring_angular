package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.Reservation;

public interface ReservationRepository extends JpaRepository<Reservation,Long> {
}
