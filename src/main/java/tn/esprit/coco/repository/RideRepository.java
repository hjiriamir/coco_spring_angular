package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.Ride;

public interface RideRepository extends JpaRepository<Ride,Long> {
}
