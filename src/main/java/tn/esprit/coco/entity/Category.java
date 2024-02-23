package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.Set;

@ToString
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long categoryID;
    private String categoryTitle;
    private String categoryDescription;

    @OneToMany(cascade = CascadeType.ALL, mappedBy="category")
    private Set<SubCategory> subCategories;
}
