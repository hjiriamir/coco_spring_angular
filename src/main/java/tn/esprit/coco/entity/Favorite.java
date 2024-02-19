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
public class Favorite {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long FavoriteID;

    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;
}
