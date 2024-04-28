package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.coco.entity.ProfilePicture;
@Repository

public interface ProfilePictureRepository extends JpaRepository<ProfilePicture,Long> {
}
