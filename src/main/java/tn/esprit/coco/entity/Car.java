package tn.esprit.coco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Car {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long CarID;
    private String Model;
    private Boolean Comfort;

    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;
    @JsonIgnore
    @OneToMany(mappedBy = "car")
    private List<Image> images;

}

