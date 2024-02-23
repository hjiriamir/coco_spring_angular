package tn.esprit.coco.entity;

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

    @OneToOne
    private User user;

    @Enumerated(value = EnumType.STRING)
    private DurationPlan durationPlan;

    private LocalDate startDate;

    private LocalDate endDate;

    @Enumerated(value = EnumType.STRING)
    private SubscriptionStatus status;

    private float SubPrice ;
}
