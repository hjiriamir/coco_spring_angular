package tn.esprit.coco.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Temporal;
import jakarta.persistence.TemporalType;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import tn.esprit.coco.entity.StateReclamation;
import tn.esprit.coco.entity.TypeReclamation;

import java.util.Date;
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class ReclamationDto {
    private String title;
    private String description;
    //private TypeReclamation type;

}
