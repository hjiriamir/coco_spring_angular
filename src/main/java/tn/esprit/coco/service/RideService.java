package tn.esprit.coco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.coco.dto.RideDto;
import tn.esprit.coco.entity.Ride;
import tn.esprit.coco.entity.User;
import tn.esprit.coco.repository.RideRepository;

import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class RideService {
    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private RideRepository rideRepository;

    public List<RideDto> getCurrentUserRides() {
        User currentUser = userDetailsService.getCurrentUser(); // Make sure this method fetches the authenticated user
        List<Ride> rides = rideRepository.findByDriverId(currentUser.getId()); // Adjust this method if needed
        return rides.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    public List<RideDto> getAllRides() {
        List<Ride> rides = rideRepository.findAll();
        return rides.stream().map(this::convertToDto).collect(Collectors.toList());
    }

    private RideDto convertToDto(Ride ride) {
        RideDto dto = new RideDto();
        dto.setId(ride.getRideID());
        dto.setStartLocation(ride.getStartLocation());
        dto.setArrivalAddress(ride.getArrivalAddress());
        dto.setDepartureDate(ride.getDepartureDate());
        dto.setTime(ride.getTime());
        dto.setPrice(ride.getPrice());
        dto.setPlaceDisponible(ride.getPlaceDisponible());
        // Set other properties as needed
        return dto;
    }


}
