package tn.esprit.coco.serviceImp;

import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

public interface ImageUpload {
    String uploadFile(MultipartFile multipartFile) throws IOException;
}
