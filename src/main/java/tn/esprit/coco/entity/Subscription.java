package tn.esprit.coco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDate;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class Subscription implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    //@Enumerated(value = EnumType.STRING)
    //private DurationPlan durationPlan;

    //private LocalDate startDate;

    //private LocalDate endDate;

    @Enumerated(value = EnumType.STRING)
    private SubscriptionStatus status;

    private double subscriptionPrice;

    private int remainingTrips;
    private int TripStops ;

    private PaymentMethod subscriptionPaymentMethod;

    private String qrCodeData;
     private  String qrCodeImageUrl;

    @OneToOne
    @JsonIgnore
    private User user;
}
