package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.coco.entity.Reclamation;
import tn.esprit.coco.entity.User;

import java.util.List;

@Repository

public interface ReclamationRepository extends JpaRepository<Reclamation,Long> {
    List<Reclamation> findByUser(User user);
}
