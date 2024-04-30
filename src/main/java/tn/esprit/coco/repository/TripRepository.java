package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.Trip;

public interface TripRepository extends JpaRepository<Trip, Long> {
}