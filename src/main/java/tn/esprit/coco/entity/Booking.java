package tn.esprit.coco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Builder
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
    @JsonIgnore
    private Set<RoomPayment> roompayments;
    @ManyToOne
    @JsonIgnore
    private Accommodation accommodations;
    @ManyToOne
    @JsonIgnore
    private User user;

}
