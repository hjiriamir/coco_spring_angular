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

    private String lieuDepart;
    private String lieuArrivee;
    private LocalDateTime heureDepart;
    private LocalDateTime heureArrivee;
    private String dureeEstimee;
    private double tarif;
    @ManyToOne
    private Bus busAffecte;



    @OneToOne
    private BusPhoto photoTrajet;



    @OneToOne
    private DailyTicket dailyTicket;

    @OneToMany(mappedBy = "trip")
    private List<TripStop> tripStops;







}
