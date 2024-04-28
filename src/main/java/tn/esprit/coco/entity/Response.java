package tn.esprit.coco.entity;

import jakarta.persistence.*;
import lombok.*;

@Entity
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class Response {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String message;
    @ManyToOne
    private Reclamation reclamation;

   @ManyToOne
   @JoinColumn(name = "responder_id")
   private User responder;

}
