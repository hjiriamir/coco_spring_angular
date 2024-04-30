package tn.esprit.coco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class Trip implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idTrip;

    private String departureLocation;
    private String arrivalLocation;
    private String estimatedDuration;
    private double fare;
    @ManyToOne
    @JsonIgnore
    private Bus assignedBus;

    /*@OneToOne
    @JsonIgnore
    private BusPhoto tripPhoto;*/

    @OneToMany(mappedBy = "trip")
    @JsonIgnore
    private List<TripStop> tripStops;









}
