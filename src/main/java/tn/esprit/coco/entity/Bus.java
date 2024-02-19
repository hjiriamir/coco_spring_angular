package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;
import org.w3c.dom.stylesheets.LinkStyle;

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

    @OneToOne(cascade = CascadeType.ALL)
    private BusPhoto photoBus;

    @OneToMany(mappedBy = "bus")
    private List<Chauffeur> chauffeurs;





}
