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
public class ColSubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subCategoryID;
    private String subCategoryTitle;
    private String subCategoryDescription;

    @ManyToOne
    private Category category;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="subcategory")
    private Set<Accommodation> accommodations;
}
