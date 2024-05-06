package tn.esprit.coco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.List;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Table(name="orders")
public class OrderProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY  )
    private Long idOrder;
    private Date date;
    private Long amount;
    private String address;
    private String payment;
    private OrderStatus orderStatus;
    private Long totalAmount;
    private Long discount;
    private UUID trackingId;
    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "user_id",referencedColumnName = "id")
    private User user;
    @OneToMany(fetch = FetchType.LAZY, mappedBy = "order")
    private List<CartItems> cartItems;
   /* private int Quantity;
    private float amount;
    private String address;
    private int postcode;
    private  String City;
    private String notes;
    @OneToOne
    private paymentProduct paymentproduct;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="orderproduct")
    private List<Product> Products;*/

}
