package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.Favorite;

public interface FavoriteRepository extends JpaRepository<Favorite,Long> {
}
