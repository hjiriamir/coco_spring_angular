package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Ride {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long RideID;
    private String StartLocation;
    private Date DepartureDate;
    private Date ArrivalDate;
    private  float price;
    @OneToMany(mappedBy = "ride")
    private List<Car> cars;

    @ManyToMany
    private List<User> passengers;
    @ManyToOne
    @JoinColumn(name = "Idride")
    private User driver;
    @OneToMany(mappedBy = "rideR")
    private List<Reservation> reservations;
    @OneToMany(mappedBy = "ride", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Favorite>favorites;
}
