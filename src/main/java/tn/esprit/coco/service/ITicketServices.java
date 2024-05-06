package tn.esprit.coco.service;

import tn.esprit.coco.entity.DailyTicket;

import java.util.List;

public interface ITicketServices {
    DailyTicket addDailyTicket( Long tripStopId);
    List<DailyTicket> getAllDailyTicket();
    DailyTicket getDailyTicket(Long iduser);
    void removeTicket(Long idTicket);
}
