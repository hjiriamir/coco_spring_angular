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
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accommodationID;
    private String address;
    private float rent_price;
    private int numberOfRoom;
    private String rules;

    @ManyToOne
    ColSubCategory subcategory;

    @ManyToOne
    Room room;
    //pere
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<FavoriteList> favoritelists;

    @ManyToOne
    private User host;
    @ManyToOne
    private User roomseeker;
}
