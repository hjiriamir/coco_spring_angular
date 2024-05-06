package tn.esprit.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.service.FacebookService;

@CrossOrigin(origins = "http://localhost:4200",methods={RequestMethod.POST,RequestMethod.PUT, RequestMethod.DELETE,RequestMethod.GET,RequestMethod.PATCH})
@RestController
@RequestMapping("/api/facebook")
public class FacebookController {

    @Autowired
    private FacebookService facebookService;

    @PostMapping("/publish")
    public ResponseEntity<String> publishOnFacebookPage(@RequestBody String message) {
        facebookService.publishOnPage(message);
        return ResponseEntity.ok("Publication réussie sur la page Facebook.");
    }
}
