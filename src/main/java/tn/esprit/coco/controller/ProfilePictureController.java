package tn.esprit.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.coco.entity.ProfilePicture;
import tn.esprit.coco.service.ProfilePictureService;

import java.io.IOException;

@RestController
@RequestMapping("/profile-picture")
@CrossOrigin("*")
public class ProfilePictureController {

    @Autowired
    private ProfilePictureService profilePictureService;

    @PostMapping("/upload")
    public ProfilePicture uploadProfilePicture(@RequestParam("image") MultipartFile file) throws IOException {
        return profilePictureService.uploadProfilePicture(file);
    }

    @GetMapping("/get/{id}")
    public ResponseEntity<byte[]> getProfilePicture(@PathVariable Long id) throws IOException {
        return profilePictureService.getProfilePicture(id);
    }

    @DeleteMapping("/delete/{id}")
    public void deleteProfilePicture(@PathVariable Long id) {
        profilePictureService.deleteProfilePicture(id);
    }
}
