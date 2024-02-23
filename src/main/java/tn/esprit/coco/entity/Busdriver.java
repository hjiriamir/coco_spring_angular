package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class Busdriver implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String nom;
    private String prenom;
    private String numeroTelephone;
    private String email;
    private String statut;
    @OneToOne(cascade = CascadeType.ALL)
    private BusPhoto photoChauffeur;
    @ManyToOne
    private Bus bus;




}
