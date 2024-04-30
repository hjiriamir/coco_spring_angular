package tn.esprit.coco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    private String address;
    private int postcode;
    private  String City;
    private String notes;
    @OneToOne
    private paymentProduct paymentproduct;
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL, mappedBy="orderproduct")
    private List<Product> Products;

}
