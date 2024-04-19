package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.Booking;

public interface BookingRepository extends JpaRepository<Booking,Long> {
}
