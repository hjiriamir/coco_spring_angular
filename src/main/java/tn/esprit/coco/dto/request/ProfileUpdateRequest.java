package tn.esprit.coco.dto.request;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;
import tn.esprit.coco.entity.ProfilePicture;

import java.time.LocalDate;
import java.util.Set;

@Getter
@Setter
public class ProfileUpdateRequest {

    private String username;
    private String address;
    private LocalDate dateOfBirth;
    private String pictureUrl;
    private ProfilePicture profilePicture;
    private Set<String> roles;


}
