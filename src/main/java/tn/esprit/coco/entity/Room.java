package tn.esprit.coco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.Set;


@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Builder
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomID;
    private String roomType;
    private float rent;
    private Long bookingPrice;
    @Temporal(TemporalType.DATE)
    private Date VisitDate;
    private String amenities;
    private String roomDetails;
    /*@ElementCollection
    private List<String> imageNames;

    @ElementCollection
    private List<String> imagePaths;*/



    @ManyToOne
    @JsonIgnore
    private Accommodation accommodations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="room")
    @JsonIgnore
    private Set<Photo> photos;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RoomPhoto> roomPhotos;
   /* @JsonIgnore
    public List<String> getImages() {
      return imagePaths;
    }*/


}
