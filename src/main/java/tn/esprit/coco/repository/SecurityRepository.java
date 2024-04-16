package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.Security;

public interface SecurityRepository extends JpaRepository<Security,Long> {
}
