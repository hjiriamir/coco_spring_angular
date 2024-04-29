package tn.esprit.coco.repository;

import com.twilio.base.Page;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import tn.esprit.coco.entity.User;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByEmail(String email);

    Boolean existsByUsername(String username);

    Boolean existsByEmail(String email);

    @Query("SELECT u FROM User u LEFT JOIN u.roles r WHERE " +
            "LOWER(u.username) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(u.email) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(u.gender) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "LOWER(r.name) LIKE LOWER(CONCAT('%',:searchTerm, '%')) OR " +
            "CAST(u.dateOfBirth AS text) LIKE CONCAT('%',:searchTerm, '%') OR " +
            "LOWER(u.phoneNumber) LIKE LOWER(CONCAT('%',:searchTerm, '%'))")
    List<User> searchByTerm(@Param("searchTerm") String searchTerm);

    @Query("SELECT u.gender, COUNT(u) FROM User u GROUP BY u.gender")
    List<Object[]> countUsersByGender();

    @Query("SELECT r.name, COUNT(u) FROM User u JOIN u.roles r GROUP BY r.name")
    List<Object[]> countUsersByRole();

}
