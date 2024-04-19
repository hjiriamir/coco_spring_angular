package tn.esprit.coco.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data @NoArgsConstructor @AllArgsConstructor @Builder
public class SubCategoryDto {
    private String subCategoryTitle;
    private String subCategoryDescription;
    private Long category;
}
