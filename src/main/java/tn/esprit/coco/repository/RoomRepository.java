package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import tn.esprit.coco.entity.Room;

@Repository
public interface RoomRepository extends JpaRepository<Room,Long> {
}
