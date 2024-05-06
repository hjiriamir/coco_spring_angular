package tn.esprit.coco.service;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import tn.esprit.coco.entity.Sold;

import java.util.Optional;

public interface SoldRepository extends JpaRepository<Sold, Long> {
    @Query("SELECT s FROM Sold s WHERE s.user.id = :userId")
    Optional<Sold> findCurrentUserSoldByIdUser(@Param("userId") Long userId);
    Optional<Sold> findByUserId(Long userId);

}
