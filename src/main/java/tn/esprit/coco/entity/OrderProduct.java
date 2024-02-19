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
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY  )
    private Long idOrder;
    private int Quantity;
    private float amount;
    //private Long Buyer_Id;
    //private  Long Seller_Id;
    private String Firstname;
    private String lastname;
    private String address;
    private int postcode;
    private  String City;
    private String notes;
    @OneToOne
    private PaymentProduct paymentproduct;
    @ManyToOne
    User user;
    @ManyToMany(mappedBy="orderproducts", cascade = CascadeType.ALL)
    private List<Product> products;

}
