package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import tn.esprit.coco.dto.ResponseDto;
import tn.esprit.coco.entity.Response;

import java.util.List;

@Repository

public interface ResponseRepository extends JpaRepository<Response,Long> {
    List<Response> findByReclamationId(Long reclamationId);
    @Query("SELECT new tn.esprit.coco.dto.ResponseDto(r.id, r.message, rec.title, u.username) " +
            "FROM Response r " +
            "JOIN r.reclamation rec " +
            "JOIN rec.user u")
    List<ResponseDto> fetchAllDetailedResponses();
}
