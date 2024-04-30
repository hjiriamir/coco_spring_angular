package tn.esprit.coco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
    private String name;
    @Enumerated(EnumType.STRING)
    private TypeProduct typeProduct;
    private String description;
    private int quantity;
    private String weight;
    private int price;

    @ManyToOne
    SubCategoryProduct subcategory;
    //@ManyToOne
    //Product product;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL)
    private List<WishList> wishlists;
    @ManyToOne
    User user;
    @ManyToOne
    OrderProduct orderproduct;
    @JsonIgnore
    @OneToMany(mappedBy="product", cascade = CascadeType.ALL)
    private List<PictureProduct> pictureProducts;

    @JsonIgnoreProperties("products")
    @ManyToMany(mappedBy = "products")
    private List<FavoriteProduct> favoritesProducts;

}
