package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY  )
    private Long idSubCategory;
    private String SubCategoryName;
    @ManyToOne
    Category category;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="subcategory")
    private List<Product> Products;
}
