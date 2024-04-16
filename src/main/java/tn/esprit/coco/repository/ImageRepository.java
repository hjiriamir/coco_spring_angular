package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.Image;

public interface ImageRepository extends JpaRepository<Image,Long> {
}
