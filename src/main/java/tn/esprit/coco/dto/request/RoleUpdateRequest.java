package tn.esprit.coco.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class RoleUpdateRequest {
    private String email;
    private String roleName;
}
