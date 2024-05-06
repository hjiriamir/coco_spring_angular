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
public class Accommodation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long accommodationID;

    private String address;
    private float rent_price;
    private int numberOfRoom;
    private String rules;
    private String localisation;
    private String accommodationName;
    private String categoryTitle;

    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;

    @OneToMany(mappedBy = "accommodations", cascade = CascadeType.ALL)

    private List<Room> rooms;


    @ManyToMany
    @JsonIgnore
    @JoinColumn(name = "favorite_list_id")
    private List<FavoriteList>  favoriteList;
    //père

    @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(
            name = "accommodation_category",
            joinColumns = @JoinColumn(name = "accommodation_id"),
            inverseJoinColumns = @JoinColumn( name = "category_id")
    )
    private Set<Category> categories;
    @ManyToMany
    @JsonIgnore
    private List<User> user;


    @OneToMany(cascade = CascadeType.ALL, mappedBy = "accommodation")
    @JsonIgnore
    private List<PhotoAccommodation> photoAccommodations;

    public String getImagePath() {
        return imagePath;
    }

    public void setImagePath(String imagePath) {
        this.imagePath = imagePath;
    }

    public String getImageName() {
        return imageName;
    }

    public void setImageName(String imageName) {
        this.imageName = imageName;
    }

    @Column(name = "image_path")
    private String imagePath;
    @Column(name = "image_name")
    private String imageName;


}
