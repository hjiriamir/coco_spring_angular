package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.Car;

public interface CarRepository extends JpaRepository<Car,Long> {
   // Car findByRideID(Long rideID);
}
