package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class paymentProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY  )
    private Long idpayment;
    @Enumerated(EnumType.STRING)
    private PaymentMethod typepaymentproduct;
    @Enumerated(EnumType.STRING)
    private Status status;
    private float Total;
    @OneToOne(mappedBy="paymentproduct")
    private OrderProduct orderproduct;
}
