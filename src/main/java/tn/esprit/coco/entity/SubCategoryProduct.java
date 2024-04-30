package tn.esprit.coco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
   // @JoinColumn(name = "idCategory")
    CategoryProduct category;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="subcategory")
    private List<Product> Products;
}
