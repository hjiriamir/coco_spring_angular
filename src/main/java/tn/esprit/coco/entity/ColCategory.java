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
public class ColCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryID;
    private String categoryTitle;
    private String categoryDescription;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="colCategory")
    private Set<ColSubCategory> ColSubCategories;
}
