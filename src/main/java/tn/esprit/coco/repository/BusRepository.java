package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.Bus;

public interface BusRepository extends JpaRepository<Bus, Long> {
}