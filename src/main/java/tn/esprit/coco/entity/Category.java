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
public class Category {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY  )
    private Long idCategory;
    private String CategoryName;
    @OneToMany(cascade = CascadeType.ALL, mappedBy="category")
    private List<SubCategory> SubCategorys;
}