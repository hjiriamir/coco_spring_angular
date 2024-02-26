package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

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

}
