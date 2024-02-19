package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Image {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long ImageID;
    private Date DateAdded;
    private String Format;
    private String Path;
    @ManyToOne
    @JoinColumn(name = "car_id")
    private Car car;
}
