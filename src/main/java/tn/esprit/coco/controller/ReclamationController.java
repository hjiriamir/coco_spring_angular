package tn.esprit.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.dto.ReclamationDto;
import tn.esprit.coco.entity.Reclamation;
import tn.esprit.coco.service.ReclamationService;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/reclamations")
public class ReclamationController {
    @Autowired
    private ReclamationService reclamationService;

    @PostMapping("/add")
    @PreAuthorize("isAuthenticated()")
    public ResponseEntity<?> addReclamation(@RequestParam Long rideId, @RequestBody ReclamationDto reclamationDto) {
        try {
            Reclamation reclamation = reclamationService.addReclamation(rideId, reclamationDto.getTitle(), reclamationDto.getDescription());
            return ResponseEntity.ok(reclamation);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Failed to add reclamation: " + e.getMessage());
        }
    }
    /*@PostMapping
    public ResponseEntity<Reclamation> addReclamation(@RequestBody Reclamation reclamation, @AuthenticationPrincipal String userEmail) {
        return ResponseEntity.ok(reclamationService.addReclamation(reclamation, userEmail));
    }

    @PutMapping("/{id}")
    public ResponseEntity<Reclamation> updateReclamation(@PathVariable Long id, @RequestBody Reclamation reclamation) {
        return ResponseEntity.ok(reclamationService.updateReclamation(id, reclamation));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteReclamation(@PathVariable Long id) {
        reclamationService.deleteReclamation(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<Reclamation>> getAllReclamations() {
        return ResponseEntity.ok(reclamationService.getAllReclamations());
    }

    @GetMapping("/user")
    public ResponseEntity<List<Reclamation>> getReclamationsForCurrentUser(@AuthenticationPrincipal String userEmail) {
        return ResponseEntity.ok(reclamationService.getReclamationsForCurrentUser(userEmail));
    }*/
}
