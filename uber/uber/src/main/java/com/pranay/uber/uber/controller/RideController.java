package com.pranay.uber.controller;

import jakarta.validation.Valid;
import com.pranay.uber.dto.CreateRideRequest;
import com.pranay.uber.exception.BadRequestException;
import com.pranay.uber.model.Ride;
import com.pranay.uber.service.RideService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/rides")
public class RideController {
    private final RideService rideService;

    public RideController(RideService rideService) { this.rideService = rideService; }

    // Create ride (Passenger)
    @PostMapping
    public ResponseEntity<Ride> createRide(@Valid @RequestBody CreateRideRequest req,
                                           Authentication authentication) {
        if (authentication == null) throw new BadRequestException("must be logged in");
        String username = (String) authentication.getPrincipal();
        // userId should be username's id, but we store only username in token. For simplicity store userId=username.
        // In production you'd map username -> userId via UserRepository.
        Ride ride = new Ride(username, req.getPickupLocation(), req.getDropLocation(), "REQUESTED");
        Ride saved = rideService.createRide(ride);
        return ResponseEntity.ok(saved);
    }

    // Complete ride (both driver & user allowed per spec)
    @PostMapping("/{rideId}/complete")
    public ResponseEntity<Ride> completeRide(@PathVariable String rideId) {
        Ride updated = rideService.completeRide(rideId);
        return ResponseEntity.ok(updated);
    }
}
