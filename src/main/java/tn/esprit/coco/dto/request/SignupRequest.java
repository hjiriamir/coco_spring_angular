package tn.esprit.coco.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class SignupRequest {

    @Email
    @NotBlank
    private String email;
    @NotBlank
    @Size(min = 6, max = 40)
    private String password;
    private String username;
    private String gender;
    private String address;
    private LocalDate dateOfBirth;
    private String pictureUrl;
    private String phoneNumber;
    private Set<String> role;

}
