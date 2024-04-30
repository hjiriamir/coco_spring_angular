package tn.esprit.coco.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.coco.service.FileUpload;

import java.io.IOException;

@Controller
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:4200",methods={RequestMethod.POST,RequestMethod.PUT,
        RequestMethod.DELETE,RequestMethod.GET,RequestMethod.PATCH})
public class FileUploadController {

    private final FileUpload fileUpload;

    @GetMapping("/")
    public String home() {
        return "home";
    }

  @PostMapping("/uploadd")
    public String uploadFile(@RequestParam("image") MultipartFile multipartFile,
                             Model model) throws IOException {
        String imageURL = fileUpload.uploadFile(multipartFile);
        model.addAttribute("imageURL",imageURL);
       // return imageURL;
         return "gallery";
    }
 /*@PostMapping("/upload")
 @ResponseBody
 public String uploadFile(@RequestParam("image") MultipartFile multipartFile) throws IOException {
     String imageURL = fileUpload.uploadFile(multipartFile);
     return imageURL;
 }*/




}
