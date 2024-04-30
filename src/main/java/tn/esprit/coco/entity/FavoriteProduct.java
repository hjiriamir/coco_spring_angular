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
public class FavoriteProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idfav;
    @ManyToOne
    private User user;

    @ManyToMany
    private List<Product> products;

    public FavoriteProduct(User user, List<Product> products) {
        this.user = user;
        this.products = products;
    }

}
