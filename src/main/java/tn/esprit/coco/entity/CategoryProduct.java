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
public class CategoryProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY  )
    private Long idCategory;
    private String categoryName;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="category", orphanRemoval = true)
    private List<SubCategoryProduct> SubCategorys;
}
