package com.pranay.uber.service;

import com.pranay.uber.exception.BadRequestException;
import com.pranay.uber.exception.NotFoundException;
import com.pranay.uber.model.Ride;
import com.pranay.uber.repository.RideRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RideService {
    private final RideRepository repo;

    public RideService(RideRepository repo) { this.repo = repo; }

    public Ride createRide(Ride ride) {
        ride.setStatus("REQUESTED");
        return repo.save(ride);
    }

    public List<Ride> getRidesByUser(String userId) {
        return repo.findByUserId(userId);
    }

    public List<Ride> getRequests() {
        return repo.findByStatus("REQUESTED");
    }

    public Ride acceptRide(String rideId, String driverId) {
        Ride ride = repo.findById(rideId).orElseThrow(() -> new NotFoundException("ride not found"));
        if (!"REQUESTED".equals(ride.getStatus())) {
            throw new BadRequestException("ride must be REQUESTED to accept");
        }
        ride.setDriverId(driverId);
        ride.setStatus("ACCEPTED");
        return repo.save(ride);
    }

    public Ride completeRide(String rideId) {
        Ride ride = repo.findById(rideId).orElseThrow(() -> new NotFoundException("ride not found"));
        if (!"ACCEPTED".equals(ride.getStatus())) {
            throw new BadRequestException("ride must be ACCEPTED to complete");
        }
        ride.setStatus("COMPLETED");
        return repo.save(ride);
    }

    public Ride findById(String id) {
        return repo.findById(id).orElseThrow(() -> new NotFoundException("ride not found"));
    }
}
