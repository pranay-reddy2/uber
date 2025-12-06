package com.pranay.uber.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class CreateRideRequest {
    @NotBlank(message = "Pickup is required")
    @Size(min = 1)
    private String pickupLocation;

    @NotBlank(message = "Drop is required")
    @Size(min = 1)
    private String dropLocation;

    public CreateRideRequest() {}
    public String getPickupLocation() { return pickupLocation; }
    public void setPickupLocation(String pickupLocation) { this.pickupLocation = pickupLocation; }
    public String getDropLocation() { return dropLocation; }
    public void setDropLocation(String dropLocation) { this.dropLocation = dropLocation; }
}
