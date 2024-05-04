package tn.esprit.coco.serviceImp;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.coco.entity.*;
import tn.esprit.coco.repository.DailyTicketRepository;
import tn.esprit.coco.repository.SubscriptionRepository;
import tn.esprit.coco.repository.TripStopRepository;
import tn.esprit.coco.repository.UserRepository;
import tn.esprit.coco.service.ISubscriptionServices;
import tn.esprit.coco.service.ITicketServices;
import tn.esprit.coco.service.UserDetailsServiceImpl;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class DailyTicketServicesImpl implements ITicketServices {
    @Autowired
    private TripStopRepository tripStopRepository;
    @Autowired
    private DailyTicketRepository   dailyTicketRepository;
    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Override
    public DailyTicket addDailyTicket( Long tripStopId) {
        User user = userDetailsService.getCurrentUser();

        TripStop tripStop = tripStopRepository.findById(tripStopId)
                .orElseThrow(() -> new IllegalArgumentException("Trip stop not found"));

        List<TripStop> tripStops = new ArrayList<>();
        tripStops.add(tripStop); // Add the selected trip stop to the list of trip stops

        DailyTicket dailyTicket = DailyTicket.builder()
                .validityDate(LocalDate.now()) // Set validity date to current date
                .status(TicketStatus.EXPIRED)   // Set status to ACTIVE
                .paymentMethod(PaymentMethod.CREDIT_CARD) // Set payment method (example)
                .user(user)
                .tripStops(tripStops)
                .price(tripStop.getAmount())
                .build();
        String qrCodeData = generateQrCodeData(dailyTicket);
        dailyTicket.setQrCodeData2(qrCodeData) ;



        return dailyTicketRepository.save(dailyTicket);
    }
    private String generateQrCodeData(DailyTicket ticket) {
        String qrCodeData = "Passenger: " + ticket.getUser().getUsername() + "\n"
                + "Validity Date: " + ticket.getValidityDate() + "\n"
                + "Price: " + ticket.getPrice();
        return qrCodeData;
    }






    @Override
    public List<DailyTicket> getAllDailyTicket() {
        return (List<DailyTicket>) dailyTicketRepository.findAll();
    }

    @Override
    public DailyTicket getDailyTicket(Long iduser) {
        User user = userDetailsService.getCurrentUser();
        return dailyTicketRepository.findDailyTicketByUserId(user.getId());
    }

    @Override
    public void removeTicket(Long idTicket) {
        dailyTicketRepository.deleteById(idTicket);

    }
    public DailyTicket updateStatus(Long ticketId, TicketStatus newStatus) {
        // Retrieve the subscription from the database
        DailyTicket ticket = dailyTicketRepository.findById(ticketId)
                .orElseThrow(() -> new EntityNotFoundException("Ticket not found with id: " + ticketId));

        // Update the status
        ticket.setStatus(newStatus);

        // Save the updated subscription back to the database
        return dailyTicketRepository.save(ticket);
    }
}
