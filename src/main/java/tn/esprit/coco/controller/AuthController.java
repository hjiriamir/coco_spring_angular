package tn.esprit.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.dto.request.LoginRequest;
import tn.esprit.coco.dto.request.RoleUpdateRequest;
import tn.esprit.coco.dto.request.SignupRequest;
import tn.esprit.coco.dto.response.JwtResponse;
import tn.esprit.coco.dto.response.MessageResponse;
import tn.esprit.coco.entity.ERole;
import tn.esprit.coco.entity.Gender;
import tn.esprit.coco.entity.Role;
import tn.esprit.coco.entity.User;
import tn.esprit.coco.repository.RoleRepository;
import tn.esprit.coco.repository.UserRepository;
import tn.esprit.coco.service.UserDetailsImpl;
import tn.esprit.coco.service.UserService;
import tn.esprit.coco.serviceImp.IUserService;
import tn.esprit.coco.utils.JwtUtils;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin("*")
@RestController
public class AuthController {
    @Autowired
    UserRepository userRepository;

    @Autowired
    RoleRepository roleRepository;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    PasswordEncoder encoder;

    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    IUserService iuserService;

    @PostMapping("/auth/signup")
    public ResponseEntity<?> registerUser(@RequestBody SignupRequest signUpRequest) {
        if (userRepository.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Email is already in use!"));
        }

        Gender genderEnum;
        try {
            genderEnum = Gender.valueOf(signUpRequest.getGender().toUpperCase());
        } catch (IllegalArgumentException | NullPointerException e) {
            return ResponseEntity.badRequest().body(new MessageResponse("Error: Invalid gender value."));
        }

        User user = User.builder()
                .username(signUpRequest.getUsername())
                .email(signUpRequest.getEmail())
                .password(encoder.encode(signUpRequest.getPassword()))
                .gender(genderEnum)
                .address(signUpRequest.getAddress())
                .dateOfBirth(signUpRequest.getDateOfBirth())
                .pictureUrl(signUpRequest.getPictureUrl())
                .build();

        Set<String> strRoles = signUpRequest.getRole();
        Set<Role> roles = new HashSet<>();
        String domain = signUpRequest.getEmail().substring(signUpRequest.getEmail().indexOf("@") + 1);

        if ("esprit.tn".equals(domain)) {
            if ("admin@esprit.tn".equalsIgnoreCase(signUpRequest.getEmail()) || "admin2@esprit.tn".equalsIgnoreCase(signUpRequest.getEmail())) {
                Role adminRole = roleRepository.findByName(ERole.ADMIN)
                        .orElseThrow(() -> new RuntimeException("Error: Role ADMIN not found."));
                roles.add(adminRole);
            } else if (strRoles == null || strRoles.isEmpty()) {
                Role userRole = roleRepository.findByName(ERole.USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role USER not found."));
                roles.add(userRole);
            } else {
                strRoles.forEach(role -> {
                    switch (role.toUpperCase()) {
                        case "DRIVER":
                            Role driverRole = roleRepository.findByName(ERole.DRIVER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role DRIVER not found."));
                            roles.add(driverRole);
                            break;
                        case "PASSENGER":
                            Role passengerRole = roleRepository.findByName(ERole.PASSENGER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role PASSENGER not found."));
                            roles.add(passengerRole);
                            break;
                        case "HOST":
                            Role hostRole = roleRepository.findByName(ERole.HOST)
                                    .orElseThrow(() -> new RuntimeException("Error: Role HOST not found."));
                            roles.add(hostRole);
                            break;
                        case "ROOMSEEKER":
                            Role roomSeekerRole = roleRepository.findByName(ERole.ROOMSEEKER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role ROOMSEEKER not found."));
                            roles.add(roomSeekerRole);
                            break;
                        default:
                            // This case can be used for validation or default role assignment
                            throw new RuntimeException("Error: Invalid role specified.");
                    }
                });
            }
        } else {
            Role externalUserRole = roleRepository.findByName(ERole.EXTERNAL_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role EXTERNAL_USER not found."));
            roles.add(externalUserRole);
        }

        user.setRoles(roles);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));
    }


    @PostMapping("/auth/login")
    public ResponseEntity<JwtResponse> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getEmail(), loginRequest.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);
        String jwt = jwtUtils.generateJwtToken(authentication); // Pass the entire authentication object

        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        JwtResponse jwtResponse = new JwtResponse(
                jwt,
                "Bearer",
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                userDetails.getGender(),
                userDetails.getAddress(),
                userDetails.getDateOfBirth(),
                userDetails.getPictureUrl(),
                userDetails.getAuthorities().stream()
                        .map(item -> item.getAuthority())
                        .collect(Collectors.toList())
        );

        return ResponseEntity.ok(jwtResponse);
    }


    @GetMapping("/users")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = iuserService.getAllUsers();
        return ResponseEntity.ok(users);
    }

/////////////////////////////////////////////////



//////////////////////////////////////////////////////


    /*@PostMapping("/user/addRole")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleUpdateRequest roleUpdateRequest) {
        User user = userRepository.findByEmail(roleUpdateRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Error: mail not found."));

        ERole newRole = ERole.valueOf(roleUpdateRequest.getRoleName().toUpperCase());
        Role role = roleRepository.findByName(newRole)
                .orElseThrow(() -> new RuntimeException("Error: Role not found."));

        user.getRoles().add(role);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Role added successfully!"));
    }*/

    @DeleteMapping("/users/{id}")
    public ResponseEntity<?> deleteUser(@PathVariable Long id, Authentication authentication) {
        String email = ""; // Initialize with an empty string

        if (authentication != null && authentication.getPrincipal() instanceof UserDetailsImpl) {
            UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
            email = userDetails.getEmail(); // Get the email of the currently authenticated user
        }

        try {
            iuserService.deleteUser(id, email);
            return ResponseEntity.ok().body(new MessageResponse("User deleted successfully"));
        } catch (RuntimeException ex) {
            return ResponseEntity.badRequest().body(new MessageResponse(ex.getMessage()));
        }
    }

///////////////////////admin yaamel update lel users ////////////////////////////
    @PutMapping("/users/{id}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<User> updateUser(@PathVariable Long id, @RequestBody User userDetails) {
        User updatedUser = iuserService.updateUser(id, userDetails);
        return ResponseEntity.ok(updatedUser);
    }

}