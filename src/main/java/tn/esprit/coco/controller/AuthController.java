package tn.esprit.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
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
import tn.esprit.coco.utils.JwtUtils;

import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;


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
            if (signUpRequest.getRole() == null || signUpRequest.getRole().isEmpty()) {
                roles.add(roleRepository.findByName(ERole.USER)
                        .orElseThrow(() -> new RuntimeException("Error: Role USER not found.")));
            } else {
                signUpRequest.getRole().forEach(role -> {
                    switch (role.toUpperCase()) {
                        case "DRIVER":
                            roles.add(roleRepository.findByName(ERole.DRIVER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role DRIVER not found.")));
                            break;
                        case "HOST":
                            roles.add(roleRepository.findByName(ERole.HOST)
                                    .orElseThrow(() -> new RuntimeException("Error: Role HOST not found.")));
                            break;
                        // Add more cases as necessary
                        default:
                            roles.add(roleRepository.findByName(ERole.USER)
                                    .orElseThrow(() -> new RuntimeException("Error: Role USER not found.")));
                    }
                });
            }
        } else {
            roles.add(roleRepository.findByName(ERole.EXTERNAL_USER)
                    .orElseThrow(() -> new RuntimeException("Error: Role EXTERNAL_USER not found.")));
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


    @PostMapping("/user/addRole")
    public ResponseEntity<?> addRoleToUser(@RequestBody RoleUpdateRequest roleUpdateRequest) {
        User user = userRepository.findByEmail(roleUpdateRequest.getEmail())
                .orElseThrow(() -> new RuntimeException("Error: mail not found."));

        ERole newRole = ERole.valueOf(roleUpdateRequest.getRoleName().toUpperCase());
        Role role = roleRepository.findByName(newRole)
                .orElseThrow(() -> new RuntimeException("Error: Role not found."));

        user.getRoles().add(role);
        userRepository.save(user);

        return ResponseEntity.ok(new MessageResponse("Role added successfully!"));
    }

}
