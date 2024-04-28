package tn.esprit.coco.serviceImp;

import org.springframework.http.ResponseEntity;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.coco.entity.ProfilePicture;

import java.io.IOException;

public interface ProfilePictureImpl {
    ProfilePicture uploadProfilePicture(MultipartFile file) throws IOException;

    ResponseEntity<byte[]> getProfilePicture (Long id )throws IOException;
    void deleteProfilePicture(Long id);
}
