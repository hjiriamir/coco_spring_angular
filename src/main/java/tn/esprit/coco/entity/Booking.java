package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.Set;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Booking {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookingID;
    private Date bookingDate;
    private Long hostID;
    private Long roomMateID;
    private Long accommodationID;
    private String status;

    //pere
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<RoomPayment> roompayments;

}
