package tn.esprit.coco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
@Builder
@Table(name = "room")
public class Room {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long roomID;
    private String roomType;
    private float rent;
    private String amenities;
    private String roomDetails;
//    @ElementCollection
//    private List<String> imageNames;
//
//    @ElementCollection
//    private List<String> imagePaths;



    @ManyToOne
    @JsonIgnore
    private Accommodation accommodations;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="room")
    @JsonIgnore
    private Set<Photo> photos;
    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JsonIgnore
    private List<RoomPhoto> roomPhotos;
//    public List<String> getImages() {
//        return imagePaths;
//    }
}

//@ToString
//@AllArgsConstructor
//@NoArgsConstructor
//@Getter
//@Setter
//@Entity
//@Builder
//public class Room {
//    @Id
//    @GeneratedValue(strategy = GenerationType.IDENTITY)
//    private Long roomID;
//    private String roomType;
//    private float rent;
//    private String amenities;
//    private String roomDetails;
////    @ElementCollection
////    private List<String> imageNames;
//
////    @ElementCollection
////    private List<String> imagePaths;
//
//
//
//    @ManyToOne
//    @JsonIgnore
//    private Accommodation accommodations;
//
//    @OneToMany(cascade = CascadeType.ALL, mappedBy="room")
//    @JsonIgnore
//    private Set<Photo> photos;
////    @OneToMany(cascade = CascadeType.ALL , mappedBy ="room")
////    private List<RoomPhoto> roomPhotos;
////    public List<String> getImages() {
////        return imagePaths;
////    }
//
//    @OneToMany(mappedBy = "room", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
//    @JsonIgnore
//    private List<RoomPhoto> roomPhotos;
//
//}
