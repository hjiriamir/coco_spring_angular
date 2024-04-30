package tn.esprit.coco.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import tn.esprit.coco.entity.DailyTicket;


public interface DailyTicketRepository extends JpaRepository<DailyTicket, Long> {
    DailyTicket findDailyTicketByUserId (Long userId);
}