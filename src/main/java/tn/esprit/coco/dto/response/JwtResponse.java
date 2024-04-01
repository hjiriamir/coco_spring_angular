package tn.esprit.coco.dto.response;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.List;

@Getter
@Setter
@AllArgsConstructor
public class JwtResponse {
    private String token;
    private String type = "Bearer";  // Constant value lel token

    private Long id;
    private String username;
    private String email;
    private String gender;
    private String address;
    private LocalDate dateOfBirth;
    private String pictureUrl;
    private String phoneNumber;
    private List<String> roles;


}
