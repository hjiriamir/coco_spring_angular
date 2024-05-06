package tn.esprit.coco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(name = "favorite_list")

public class FavoriteList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)

    private Long id;

    @OneToOne
    @JoinColumn(name = "user_id")
    private User user;

    private Long accommodationID;


    @ManyToMany (mappedBy = "favoriteList", cascade = CascadeType.ALL)
    @JsonIgnore
    @Builder.Default
    private List<Accommodation> accommodations = new ArrayList<>();
}
