package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class RoomPayment {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String type;
    private Long bookingID;
    private Long accommodationID;
    private float amount;

    //fils
    @ManyToMany(mappedBy="roompayments", cascade = CascadeType.ALL)
    private Set<Booking> bookings;

}
