package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.coco.entity.Ride;

import java.util.List;
@Repository
public interface RideRepository extends JpaRepository<Ride,Long> {
    List<Ride> findByDriverId(Long driverId);
    List<Ride> findByPassengersId(Long passengerId);
}
