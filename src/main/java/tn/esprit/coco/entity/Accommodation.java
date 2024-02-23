package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;
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
    private String  localisation;

    @ManyToOne
    private SubCategory subcategory;

    @OneToMany(mappedBy ="accommodations")
    private List<Room> rooms;

    //pere
    @ManyToMany(cascade = CascadeType.ALL)
    private Set<FavoriteList> favoritelists;

    @ManyToMany
    private List<User> user;

    @OneToMany(mappedBy ="accommodations")
    private List<Booking> bookings;

    @OneToMany(mappedBy ="accommodation")
    private List<PhotoAccommodation> photoAccommodations;
}
