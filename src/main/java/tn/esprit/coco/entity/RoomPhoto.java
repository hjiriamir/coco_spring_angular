package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class RoomPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String path;
    private String title;


    @ManyToOne
    private Room room;
}