package tn.esprit.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.entity.Response;
import tn.esprit.coco.service.ResponseService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/responses")
public class ResponseController {
   /* @Autowired
    private ResponseService responseService;

    @PostMapping("/add/{reclamationId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<Response> addResponseToReclamation(@PathVariable Long reclamationId, @RequestBody String message, @AuthenticationPrincipal String userEmail) {
        return ResponseEntity.ok(responseService.addResponse(reclamationId, message, userEmail));
    }

    @GetMapping("/")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Response>> getAllResponses() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        String userEmail = authentication.getName(); // Assuming the username is the email
        return ResponseEntity.ok(responseService.getAllResponses(userEmail));
    }*/


}
