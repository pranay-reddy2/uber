package com.pranay.uber.controller;

import com.pranay.uber.model.Ride;
import com.pranay.uber.service.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/driver/rides")
public class DriverController {
    private final RideService rideService;

    public DriverController(RideService rideService) { this.rideService = rideService; }

    // View pending requests
    @GetMapping("/requests")
    public ResponseEntity<List<Ride>> getPendingRequests() {
        return ResponseEntity.ok(rideService.getRequests());
    }

    // Accept a ride
    @PostMapping("/{rideId}/accept")
    public ResponseEntity<Ride> acceptRide(@PathVariable String rideId, Authentication authentication) {
        if (authentication == null) throw new RuntimeException("must be logged in as driver");
        String driverUsername = (String) authentication.getPrincipal();
        Ride updated = rideService.acceptRide(rideId, driverUsername);
        return ResponseEntity.ok(updated);
    }
}
