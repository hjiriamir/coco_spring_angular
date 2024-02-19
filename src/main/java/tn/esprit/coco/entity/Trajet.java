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
public class Trajet implements Serializable {
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

    @ManyToOne
    private Chauffeur chauffeurAffecte;

    @ManyToOne
    private Photo photoTrajet;

    @ManyToMany
    private List<Arret> arretsAssocies;

    @ManyToOne
    private Subscription subscription;





}
