package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.coco.entity.RoomPhoto;


@Repository
public interface RoomPhotoRepository extends JpaRepository<RoomPhoto,Long> {
}
