package tn.esprit.coco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String ArrivalAddress;
    private String DepartureDate;
    private String time;
    private  int price;
    private long PlaceDisponible;
    private boolean Smoking_Vehicle;
    private String preference;
    private boolean AvoidTolls;
    private String TempsEstime;
    private String Distance;
    //image
    private Date DateAddedImage;
    private String timeImage;
    private String FormatImage;
    private String PathImage;
    private String DescriptionImage;
    //car
    private String ModelCar;
    private Boolean ComfortCar;
    private String carburant;
    @JsonIgnore
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
