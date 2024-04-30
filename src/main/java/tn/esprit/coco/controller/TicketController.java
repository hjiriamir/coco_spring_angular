package tn.esprit.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.entity.DailyTicket;

import tn.esprit.coco.entity.Subscription;
import tn.esprit.coco.entity.SubscriptionStatus;
import tn.esprit.coco.entity.TicketStatus;
import tn.esprit.coco.serviceImp.DailyTicketServicesImpl;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/ticket")
public class TicketController {
    @Autowired
    private DailyTicketServicesImpl ticketService;

    @PostMapping("/add")
    public ResponseEntity<?> addTicket( @RequestBody Long tripStopId) {
        DailyTicket addedDailyTicket = ticketService.addDailyTicket( tripStopId);
        if (addedDailyTicket != null) {
            return ResponseEntity.ok(addedDailyTicket);
        } else {
            return ResponseEntity.badRequest().body("Failed to add ticket.");
        }
    }

    @GetMapping("/get-all")
    public List<DailyTicket> getAllTickets(){
        return  ticketService.getAllDailyTicket();
    }

    @GetMapping("/get/{id}")
    public DailyTicket getTicket(@PathVariable("id") Long iduser){
        return  ticketService.getDailyTicket(iduser);
    }




    @DeleteMapping("/remove/{id}")
    public void removeTicket(@PathVariable("id") Long idTicket){
        ticketService.removeTicket(idTicket);
    }
    @PutMapping("/{ticketId}/updateStatus/{newStatus}")
    public DailyTicket updateTicketStatus(@PathVariable Long ticketId, @PathVariable TicketStatus newStatus) {

        DailyTicket updatedTicket = ticketService.updateStatus(ticketId, newStatus);

        return updatedTicket;
    }

}
