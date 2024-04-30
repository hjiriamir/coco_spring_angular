package tn.esprit.coco.dto;

import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import tn.esprit.coco.entity.TypeProduct;

public class ProductDto {
    private Long idProduct;
    private String name;
    @Enumerated(EnumType.STRING)
    private TypeProduct typeProduct;
    private String description;
    private int quantity;
    private String weight;
    private int price;


}
