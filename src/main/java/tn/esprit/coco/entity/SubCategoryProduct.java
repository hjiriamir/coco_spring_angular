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
public class SubCategoryProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY  )
    private Long idSubCategory;
    private String SubCategoryName;
    @ManyToOne
    CategoryProduct category;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="subcategory")
    private List<Product> Products;
}
