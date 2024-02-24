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
public class Product {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY  )
    private Long idProduct;
    private String Name;
    @Enumerated(EnumType.STRING)
    private TypeProduct typeProduct;
    private String Description;
    private int quantity;
    private float weight;
    private float price;

    @ManyToOne
    SubCategoryProduct subcategory;
    //@ManyToOne
    //Product product;
    @ManyToMany(cascade = CascadeType.ALL)
    private List<WishList> wishlists;
    @ManyToOne
    User user;
    @ManyToOne
    OrderProduct orderproduct;

    @OneToMany(mappedBy="product", cascade = CascadeType.ALL)
    private List<PictureProduct> pictureProducts;



}
