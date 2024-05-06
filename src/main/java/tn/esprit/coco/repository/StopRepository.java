package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.Stop;
import tn.esprit.coco.entity.TripStop;

public interface StopRepository extends JpaRepository<Stop, Long> {
     Stop findByTripStops(TripStop tripStop);


}