package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Security {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long SecurityID;
    private String sharedData;
    private Boolean isActivated;
    @OneToOne(mappedBy = "security", cascade = CascadeType.ALL, orphanRemoval = true)
    private Reservation reservation;
}
