package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.coco.entity.CustomerData;

@Repository
public interface CustomerDataRepository extends JpaRepository<CustomerData, String> {
}

