package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.coco.entity.Accommodation;

import java.util.Optional;

@Repository
public interface AccommodationRepository extends JpaRepository<Accommodation,Long> {
    @Query("SELECT a FROM Accommodation a LEFT JOIN FETCH a.category WHERE a.accommodationID = :id")
    Optional<Accommodation> findAccommodationByIdWithCategory(@Param("id") Long id);
}
