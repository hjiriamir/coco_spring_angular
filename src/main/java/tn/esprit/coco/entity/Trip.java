package tn.esprit.coco.entity;

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
    private Long id;

    private String departureLocation;
    private String arrivalLocation;
    private String estimatedDuration;
    private double fare;
    @ManyToOne
    private Bus assignedBus;

    @OneToOne
    private BusPhoto tripPhoto;

    @OneToMany(mappedBy = "trip")
    private List<TripStop> tripStops;







}
