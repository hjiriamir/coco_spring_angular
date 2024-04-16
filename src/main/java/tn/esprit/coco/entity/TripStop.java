package tn.esprit.coco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class TripStop implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    //@Temporal(value = TemporalType.DATE)
    private LocalDateTime arrivalTime;
    //@Temporal(value = TemporalType.DATE)
    private LocalDateTime departureTime;

    private Double amount ;


    @ManyToOne

    private Stop stop;

    @ManyToOne

    private Trip trip;

    @ManyToMany(mappedBy = "tripStops")
    @JsonIgnore
    private List<DailyTicket> dailyTickets ;

   


}
