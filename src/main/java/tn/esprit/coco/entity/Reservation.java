package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ReservationID;
    private Date ReservationDate;
    private long PassengerID;

    @OneToOne(mappedBy = "reservation")
    private Payment payment;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User passenger;
    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride rideR;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "security_id")
    private Security security;
}
