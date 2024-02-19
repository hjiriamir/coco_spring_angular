package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Set;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomID;
    private String type;
    private float rent;
    private String amenities;
    private String roomDetails;
    private Long accommodationID;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="room")
    private Set<Accommodation> accommodations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="room")
    private Set<RoomPhoto> photos;

}