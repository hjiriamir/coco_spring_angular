package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class DailyTicket implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @OneToOne
    private User user ;

    @OneToOne
    private Trip trip;


    private LocalDate dateValidite;
    @Enumerated(value = EnumType.STRING)
    private StatutTicket statut;

}
