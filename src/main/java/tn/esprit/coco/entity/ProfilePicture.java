package tn.esprit.coco.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ProfilePicture {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long idPicture;
    private String name;
    private String type;
    @Column(name="ProfilePicture",length = 4048576)
    @Lob
    private byte[]image;
    @JsonIgnore
    @OneToOne
    private User user;
}
