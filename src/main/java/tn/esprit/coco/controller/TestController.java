package tn.esprit.coco.controller;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/test")
@CrossOrigin("*")
public class TestController {


    // Endpoint accessible by anyone
    @GetMapping("/public")
    public String publicAccess() {
        return "Public Content - Accessible by anyone.";
    }


    @GetMapping("/user")
    @PreAuthorize("hasRole('USER')")
    public String userAccess() {
        return "User Content - Accessible by any authenticated user.";
    }


    @GetMapping("/driver")
    @PreAuthorize("hasRole('DRIVER')")
    public String driverAccess() {
        return "Driver Content - Accessible by drivers.";
    }

    @GetMapping("/host")
    @PreAuthorize("hasRole('HOST')")
    public String hostAccess() {
        return "Host Content - Accessible by hosts offering accommodation.";
    }

    @GetMapping("/external")
    @PreAuthorize("hasRole('EXTERNAL_USER')")
    public String externalUserAccess() {
        return "External User Content - Accessible by external landlords or bus drivers.";
    }

    @GetMapping("/admin")
    @PreAuthorize("hasRole('ADMIN')")
    public String adminAccess() {
        return "Admin Board.";
    }
}
