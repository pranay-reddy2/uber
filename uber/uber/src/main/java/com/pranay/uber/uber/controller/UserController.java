package com.pranay.uber.controller;

import com.pranay.uber.model.Ride;
import com.pranay.uber.service.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/user")
public class UserController {
    private final RideService rideService;

    public UserController(RideService rideService) { this.rideService = rideService; }

    // Get rides of logged in user
    @GetMapping("/rides")
    public ResponseEntity<List<Ride>> getUserRides(Authentication authentication) {
        if (authentication == null) return ResponseEntity.badRequest().build();
        String username = (String) authentication.getPrincipal();
        List<Ride> rides = rideService.getRidesByUser(username);
        return ResponseEntity.ok(rides);
    }
}
