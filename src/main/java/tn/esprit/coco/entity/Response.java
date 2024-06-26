package tn.esprit.coco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
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

    ///////////
    @ManyToOne
    @JsonIgnore
    private Reclamation reclamation;

   @ManyToOne
   @JsonIgnore
   @JoinColumn(name = "responder_id")
   private User responder;

}
