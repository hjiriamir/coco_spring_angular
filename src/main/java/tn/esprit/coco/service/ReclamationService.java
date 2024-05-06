package tn.esprit.coco.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.esprit.coco.configuration.BadWordFilter;
import tn.esprit.coco.dto.ReclamationDto;
import tn.esprit.coco.entity.*;
import tn.esprit.coco.repository.*;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ReclamationService {
    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;
    @Autowired
    private  NotificationService notificationService;


    @Transactional
    public Reclamation addReclamation(Long rideId, String title, String description, TypeReclamation type) {
        User currentUser = userDetailsService.getCurrentUser();
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found with id: " + rideId));

        description = BadWordFilter.filterText(description);

        Reclamation reclamation = new Reclamation();
        reclamation.setTitle(title);
        reclamation.setDescription(description);
        reclamation.setType(type);
        reclamation.setUser(currentUser);
        reclamation.setRide(ride);
        reclamation.setState(StateReclamation.PENDING);
        reclamation.setDate(new Date());

        Reclamation savedReclamation = reclamationRepository.save(reclamation);

        // Send notification after reclamation is saved
        String notificationMessage = "New reclamation created: " + reclamation.getTitle() + " by " + currentUser.getUsername();
        notificationService.sendReclamationNotification(notificationMessage);

        return savedReclamation;
        //return reclamationRepository.save(reclamation);
    }

///////////////////////////////

    public List<ReclamationDto> getMyReclamations() {
        User currentUser = userDetailsService.getCurrentUser();
        List<Reclamation> reclamations = reclamationRepository.findByUser(currentUser);
        return reclamations.stream().map(this::convertToDto).collect(Collectors.toList());
    }


    @Transactional
    public ReclamationDto updateReclamation(Long reclamationId, ReclamationDto reclamationDto) {
        Reclamation reclamation = reclamationRepository.findById(reclamationId)
                .orElseThrow(() -> new RuntimeException("Reclamation not found with id: " + reclamationId));

        if (reclamation.getState() != StateReclamation.PENDING) {
            throw new IllegalStateException("Reclamation cannot be updated as it is no longer in PENDING status");
        }

        reclamation.setTitle(reclamationDto.getTitle());
        reclamation.setDescription(reclamationDto.getDescription());

        return convertToDto(reclamationRepository.save(reclamation));
    }


    @Transactional
    public void deleteReclamation(Long reclamationId) {
        Reclamation reclamation = reclamationRepository.findById(reclamationId)
                .orElseThrow(() -> new RuntimeException("Reclamation not found with id: " + reclamationId));
        if (reclamation.getState() != StateReclamation.PENDING) {
            throw new IllegalStateException("Reclamation can only be deleted if it is in PENDING status.");
        }

        reclamationRepository.delete(reclamation);
    }


    @Transactional
    public ReclamationDto updateReclamationState(Long reclamationId, StateReclamation newState) {
        Reclamation reclamation = reclamationRepository.findById(reclamationId)
                .orElseThrow(() -> new RuntimeException("Reclamation not found with id: " + reclamationId));

        if (reclamation.getState() == StateReclamation.CLOSED) {
            throw new IllegalStateException("Reclamation is closed and cannot be updated.");
        }

        reclamation.setState(newState);
        reclamation = reclamationRepository.save(reclamation);
        return convertToDto(reclamation);
    }





    public List<ReclamationDto> getAllReclamations() {
        List<Reclamation> reclamations = reclamationRepository.findAll();
        return reclamations.stream()
                .map(this::convertToDto)
                .collect(Collectors.toList());
    }

    private ReclamationDto convertToDto(Reclamation reclamation) {
        ReclamationDto dto = new ReclamationDto();
        dto.setId(reclamation.getId());
        dto.setTitle(reclamation.getTitle());
        dto.setDescription(reclamation.getDescription());
        dto.setType(reclamation.getType());
        dto.setDate(reclamation.getDate());
        dto.setState(reclamation.getState());
        dto.setUserId(reclamation.getUser() != null ? reclamation.getUser().getId() : null);
        dto.setRideId(reclamation.getRide() != null ? reclamation.getRide().getRideID() : null);
        return dto;
    }



    public ReclamationDto getReclamationById(Long reclamationId) {
        Reclamation reclamation = reclamationRepository.findById(reclamationId)
                .orElseThrow(() -> new RuntimeException("Reclamation not found with id: " + reclamationId));
        return convertToDto(reclamation);
    }








}
