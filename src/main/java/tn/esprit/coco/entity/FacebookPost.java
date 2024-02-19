package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class FacebookPost {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long PostID;
    private Date DatePublication;
    @OneToOne
    private Ride rideP;
}
