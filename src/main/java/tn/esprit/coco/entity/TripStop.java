package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class TripStop implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private LocalDateTime arrivalTime;
    private LocalDateTime departureTime;

    @ManyToOne
    private Stop stop;

    @ManyToOne
    private Trip trip;

   


}
