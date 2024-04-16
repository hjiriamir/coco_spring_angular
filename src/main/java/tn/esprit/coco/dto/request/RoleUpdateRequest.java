package tn.esprit.coco.dto.request;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@AllArgsConstructor
public class RoleUpdateRequest {
    private String email;
    private String roleName;
    private Set<String> roles;
}
