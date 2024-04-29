package tn.esprit.coco.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.dto.ReclamationDto;
import tn.esprit.coco.dto.response.MessageResponse;
import tn.esprit.coco.entity.Reclamation;
import tn.esprit.coco.entity.StateReclamation;
import tn.esprit.coco.service.ReclamationService;

import java.util.List;

@RestController
@Slf4j
@CrossOrigin("*")
@RequestMapping("/reclamations")
public class ReclamationController {
    @Autowired
    private ReclamationService reclamationService;

    @PostMapping("/add")
    public ResponseEntity<String> addReclamation(@RequestParam Long rideId, @RequestBody ReclamationDto reclamationDto) {
        reclamationService.addReclamation(rideId, reclamationDto.getTitle(), reclamationDto.getDescription());
        return ResponseEntity.ok("Reclamation added successfully");
    }


    @PutMapping("/{reclamationId}")
    public ResponseEntity<?> updateReclamation(@PathVariable Long reclamationId, @RequestBody ReclamationDto reclamationDto) {
        try {
            ReclamationDto updatedReclamation = reclamationService.updateReclamation(reclamationId, reclamationDto);
            return ResponseEntity.ok(updatedReclamation);
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Reclamation not found"));
        }
    }

    @DeleteMapping("/{reclamationId}")
    public ResponseEntity<?> deleteReclamation(@PathVariable Long reclamationId) {
        try {
            reclamationService.deleteReclamation(reclamationId);
            return ResponseEntity.ok(new MessageResponse("Reclamation deleted successfully"));
        } catch (RuntimeException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(new MessageResponse("Reclamation not found"));
        }
    }

    @PutMapping("/{reclamationId}/state")
    //@PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<?> updateReclamationState(@PathVariable Long reclamationId, @RequestParam("state") StateReclamation state) {
        try {
            Reclamation updatedReclamation = reclamationService.updateReclamationState(reclamationId, state);
            return ResponseEntity.ok(updatedReclamation);
        } catch (RuntimeException e) { // Catch any runtime exceptions including IllegalStateException
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ReclamationDto>> getAllReclamations() {
        List<ReclamationDto> reclamations = reclamationService.getAllReclamations();
        return ResponseEntity.ok(reclamations);
    }

    @GetMapping("/my-reclamations")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<List<ReclamationDto>> getMyReclamations() {
        try {
            List<ReclamationDto> reclamations = reclamationService.getMyReclamations();
            return ResponseEntity.ok(reclamations);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }


}
