package tn.esprit.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tn.esprit.coco.dto.GenderStatsDTO;
import tn.esprit.coco.dto.UserRoleStatsDTO;
import tn.esprit.coco.service.UserService;

import java.util.List;
import java.util.Map;

@CrossOrigin("*")
@RestController
@RequestMapping("/stats")
public class StatsController {
    @Autowired
    private UserService userService;
    @Autowired
    public StatsController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/roles")
    public ResponseEntity<List<UserRoleStatsDTO>> getUserRoleStats() {
        List<UserRoleStatsDTO> stats = userService.getUserRoleStatistics();
        return ResponseEntity.ok(stats);
    }

    @GetMapping("/gender")
    public ResponseEntity<List<GenderStatsDTO>> getGenderStats() {
        return ResponseEntity.ok(userService.getGenderStatistics());
    }

    /*@GetMapping("/roles")
    public ResponseEntity<List<UserRoleStatsDTO>> getUserRoleStats() {
        return ResponseEntity.ok(userService.getUserRoleStatistics());
    }*/


}
