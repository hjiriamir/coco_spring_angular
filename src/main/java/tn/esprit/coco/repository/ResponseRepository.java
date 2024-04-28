package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.coco.entity.Response;

import java.util.List;

@Repository

public interface ResponseRepository extends JpaRepository<Response,Long> {
    List<Response> findByReclamationId(Long reclamationId);
}
