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
public class WishList {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY  )
    private Long idList;
    //user
    @ManyToMany(mappedBy="wishlists", cascade = CascadeType.ALL)
    private List<Product> products;
    @OneToOne
    private User user;
}
