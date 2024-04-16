package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name = "picture_product")
public class PictureProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY  )
    private Long idpicture;
   // private Date dateAdded;
   // private String Format;
   // private String Path;
    private String name;
    private String imageUrl;
    private String imageId;

    public PictureProduct(String name, String imageUrl , String imageId){
        this.name = name;
        this.imageUrl = imageUrl;
        this.imageId = imageId ;
    }





    @ManyToOne
    private Product product;


}
