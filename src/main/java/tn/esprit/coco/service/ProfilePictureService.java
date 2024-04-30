package tn.esprit.coco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.coco.entity.ProfilePicture;
import tn.esprit.coco.repository.ProfilePictureRepository;
import tn.esprit.coco.serviceImp.ProfilePictureImpl;

import java.io.IOException;
import java.util.Optional;

@Service
public class ProfilePictureService implements ProfilePictureImpl {

    @Autowired
    ProfilePictureRepository profilePictureRepository;
    @Override
    public ProfilePicture uploadProfilePicture(MultipartFile file) throws IOException {
        return profilePictureRepository.save(ProfilePicture.builder()
                .name(file.getOriginalFilename())
                .type(file.getContentType())
                .image(file.getBytes()).build());
    }

    @Override
    public ResponseEntity<byte[]> getProfilePicture(Long id) throws IOException {
        final Optional<ProfilePicture> dbImage=profilePictureRepository.findById(id);
        return ResponseEntity.ok().contentType(MediaType.valueOf(dbImage.get().getType())).body(dbImage.get().getImage());
    }

    @Override
    public void deleteProfilePicture(Long id) {

        profilePictureRepository.deleteById(id);
    }
}
