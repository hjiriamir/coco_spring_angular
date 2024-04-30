package tn.esprit.coco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import jakarta.persistence.*;
import lombok.*;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor

public class Reclamation {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String description;

    @Enumerated(EnumType.STRING)
    private TypeReclamation type;

    @Temporal(TemporalType.TIMESTAMP)
    private Date date;
    @Enumerated(EnumType.STRING)
    private StateReclamation state;

    ////////////////
    @ManyToOne(fetch = FetchType.LAZY)
    @JsonManagedReference
    private User user;

    @OneToMany(cascade = CascadeType.PERSIST, mappedBy = "reclamation")
    private Set<Response> responses = new HashSet<>();


    @ManyToOne
    @JoinColumn(name = "ride_id")
    private Ride ride;

}
