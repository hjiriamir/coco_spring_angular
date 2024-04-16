package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.coco.entity.Imagee;

import java.util.List;

@Repository
public interface ImageeRepository extends JpaRepository<Imagee,Integer> {
    List<Imagee> findByOrderById();
}
