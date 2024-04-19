package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class SubCategory {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long subCategoryID;
    private String subCategoryTitle;
    private String subCategoryDescription;

    @ManyToOne
    private Category category;


}
