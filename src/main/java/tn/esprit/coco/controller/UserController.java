package tn.esprit.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.dto.request.ChangePasswordRequest;
import tn.esprit.coco.dto.request.ProfileUpdateRequest;
import tn.esprit.coco.dto.response.MessageResponse;
import tn.esprit.coco.entity.User;
import tn.esprit.coco.service.EmailService;
import tn.esprit.coco.service.UserDetailsImpl;
import tn.esprit.coco.serviceImp.IUserService;

import java.util.List;

@RestController
@RequestMapping("/user")
@CrossOrigin("*")
public class UserController {

    @Autowired
    IUserService iuserService;


    @GetMapping("/profile")
    public ResponseEntity<User> getMyProfile(Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String email = userDetails.getEmail();
        User user = iuserService.getUserByEmail(email);
        return ResponseEntity.ok(user);
    }

    @PutMapping("/profile")
    public ResponseEntity<?> updateProfile(@RequestBody ProfileUpdateRequest profileUpdateRequest, Authentication authentication) {
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();
        String email = userDetails.getEmail();

        iuserService.updateUserProfile(email, profileUpdateRequest);
        return ResponseEntity.ok(new MessageResponse("User profile updated successfully"));
    }

    @PostMapping("/change-password")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> changePassword(@RequestBody ChangePasswordRequest request) {
        boolean success = iuserService.changePassword(request.getEmail(), request.getOldPassword(), request.getNewPassword());

        if(success) {
            return ResponseEntity.ok().body("Password changed successfully");
        } else {
            return ResponseEntity.badRequest().body("Invalid email or password");
        }
    }


    @GetMapping("getuserid/{id}")
    public ResponseEntity<User> getUserById(@PathVariable Long id) {
        User user = iuserService.getUserById(id);
        return ResponseEntity.ok(user);
    }

    @PostMapping("/create")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        User newUser = iuserService.createUser(user);
        return ResponseEntity.ok(newUser);
    }

    @GetMapping("/all")
    public ResponseEntity<List<User>> getAllUsers() {
        List<User> users = iuserService.getAllUsers();
        return ResponseEntity.ok(users);
    }

}
