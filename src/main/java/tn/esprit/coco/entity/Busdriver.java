package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Entity
@Builder
public class Busdriver implements Serializable {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String firstName;
    private String phoneNumber;
    private String email;
    private String status;
    //@OneToOne(cascade = CascadeType.ALL)
    //private BusPhoto driverPhoto;

    @ManyToOne
    private Bus bus;




}
