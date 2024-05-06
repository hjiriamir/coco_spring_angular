package tn.esprit.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.entity.Sold;
import tn.esprit.coco.service.SoldService;

import java.util.Optional;

@CrossOrigin(origins = "http://localhost:4200",methods={RequestMethod.POST,RequestMethod.PUT, RequestMethod.DELETE,RequestMethod.GET,RequestMethod.PATCH,RequestMethod.OPTIONS})
@RestController
@RequestMapping("/api/sold")
public class SoldController {

    @Autowired
    private SoldService soldService;

    @PostMapping("/create")
    public ResponseEntity<Sold> createSoldForUser(@RequestParam Long userId, @RequestParam Long accountSoldValue) {
        Sold sold = soldService.createSoldForUser(userId, accountSoldValue);
        return ResponseEntity.ok(sold);
    }
    @GetMapping("/{userId}")
    public ResponseEntity<Sold> findCurrentUserSoldByIdUser(@PathVariable Long userId) {
        Optional<Sold> sold = soldService.findCurrentUserSoldByIdUser(userId);
        return sold.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @PutMapping("/update/{userId}")
    public ResponseEntity<Sold> updateSoldForUser(@PathVariable Long userId, @RequestParam Long newAccountSoldValue) {
        Sold updatedSold = soldService.updateSoldForUser(userId, newAccountSoldValue);
        return ResponseEntity.ok(updatedSold);
    }

}
