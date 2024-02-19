package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@ToString
@Entity
public class Payment {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long PaymentID;
    private float Amount;
    private String PaymentStatus;
    @Enumerated(value = EnumType.STRING)
    private TypePayment typePayment;
    @OneToOne
    private Reservation reservation;

}
