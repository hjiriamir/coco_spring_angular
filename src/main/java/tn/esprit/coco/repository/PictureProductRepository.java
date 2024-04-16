package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.coco.entity.PictureProduct;

import java.util.List;

@Repository
public interface PictureProductRepository extends JpaRepository<PictureProduct, Long> {
    List<PictureProduct> findByOrderByIdpicture();
    PictureProduct findFirstByOrderByIdpictureDesc();
    @Query("SELECT MAX(p.idpicture) FROM PictureProduct p")
    Long getLastPictureId();
}
