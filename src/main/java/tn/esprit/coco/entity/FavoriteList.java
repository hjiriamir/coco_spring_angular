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
public class FavoriteList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long accommodationID;
    private String title;
    private String description;

    //fils
    @ManyToMany(mappedBy="favoritelists", cascade = CascadeType.ALL)
    private Set<Accommodation> accommodations;
}
