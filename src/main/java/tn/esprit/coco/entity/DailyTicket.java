package tn.esprit.coco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class DailyTicket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDate validityDate;
    @Enumerated(value = EnumType.STRING)
    private TicketStatus status;

    private PaymentMethod paymentMethod;

    @OneToOne
    private User user;
    @ManyToMany
    @JsonIgnore
    private List<TripStop> tripStops ;

}
