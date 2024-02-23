package tn.esprit.coco.entity;

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
    private String numeroPlaqueImmatriculation;
    private String marque;
    private String modele;
    private int capaciteSieges;
    private String disponibilite;
    private String coordonneesGPS;



    @OneToMany
    private List<Busdriver> Busdrivers;

    @OneToMany
    private List<Trip> Trips;


   


}
