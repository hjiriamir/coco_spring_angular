package tn.esprit.coco.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.dto.ResponseDto;
import tn.esprit.coco.entity.Response;
import tn.esprit.coco.service.ResponseService;

import java.util.List;

@RestController
@CrossOrigin("*")
@Slf4j
@RequestMapping("/responses")
public class ResponseController {
    @Autowired
    private ResponseService responseService;

    @PostMapping("/add/{reclamationId}")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")
    public ResponseEntity<Response> addResponse(@PathVariable Long reclamationId, @RequestBody ResponseDto responseDto) {
        Response response = responseService.addResponse(reclamationId, responseDto.getMessage());
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<List<ResponseDto>> getAllResponses() {
        List<ResponseDto> responses = responseService.getAllResponses();
        return ResponseEntity.ok(responses);
    }

    @DeleteMapping("/{responseId}")
    @PreAuthorize("hasRole('ADMIN')")
    public ResponseEntity<String> deleteResponse(@PathVariable Long responseId) {
        try {
            responseService.deleteResponse(responseId);
            return ResponseEntity.ok("Response deleted successfully");
        } catch (RuntimeException ex) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Response not found");
        }
    }

    @GetMapping("/by-reclamation/{reclamationId}")
    public ResponseEntity<List<Response>> getResponsesByReclamation(@PathVariable Long reclamationId) {
        log.debug("Fetching responses for reclamation ID: {}", reclamationId);
        List<Response> responses = responseService.getResponsesForReclamation(reclamationId);
        return ResponseEntity.ok(responses);
    }


}
