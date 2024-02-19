package tn.esprit.coco.entity;

import jakarta.persistence.*;
import jakarta.transaction.Status;
import lombok.*;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
public class PaymentProduct {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY  )
    private Long idpayment;
    @Enumerated(EnumType.STRING)
    private Typepaymentproduct typepaymentproduct;
    @Enumerated(EnumType.STRING)
    private Status status;
    private float Total;
    @OneToOne(mappedBy="paymentproduct")
    private OrderProduct orderproduct;
}
