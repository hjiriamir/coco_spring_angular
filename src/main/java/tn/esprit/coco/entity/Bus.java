package tn.esprit.coco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class Bus implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String licensePlateNumber;
    private String brand;
    private String model;
    private int seatingCapacity;
    private String availability;
    private String gpsCoordinates;


    @OneToMany
    @JsonIgnore
    private List<Busdriver> Busdrivers;

    @OneToMany
    @JsonIgnore
    private List<Trip> Trips;

}
