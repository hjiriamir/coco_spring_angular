package tn.esprit.coco.dto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@NoArgsConstructor @AllArgsConstructor @Builder @Data
public class CategoryDto {
    private String categoryTitle;
    private String categoryDescription;
}
