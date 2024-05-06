package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.FavoriteList;

import java.util.List;

public interface FavoriteListRepository extends JpaRepository<FavoriteList, Long> {
    List<FavoriteList> findByUserId(Long userId);

}
