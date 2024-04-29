package tn.esprit.coco.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.esprit.coco.dto.ReclamationDto;
import tn.esprit.coco.entity.*;
import tn.esprit.coco.repository.ReclamationRepository;
import tn.esprit.coco.repository.RideRepository;
import tn.esprit.coco.repository.UserRepository;

import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ReclamationService {
    @Autowired
    private ReclamationRepository reclamationRepository;

    @Autowired
    private RideRepository rideRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    @Transactional
    public Reclamation addReclamation(Long rideId, String title, String description) {
        User currentUser = userDetailsService.getCurrentUser();
        Ride ride = rideRepository.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found with id: " + rideId));

        Reclamation reclamation = new Reclamation();
        reclamation.setTitle(title);
        reclamation.setDescription(description);
        reclamation.setUser(currentUser);
        reclamation.setRide(ride);
        reclamation.setState(StateReclamation.PENDING);


        return reclamationRepository.save(reclamation);
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
    public Reclamation updateReclamationState(Long reclamationId, StateReclamation newState) {
        Reclamation reclamation = reclamationRepository.findById(reclamationId)
                .orElseThrow(() -> new RuntimeException("Reclamation not found with id: " + reclamationId));

        if (reclamation.getState() == StateReclamation.PENDING) {
            reclamation.setState(newState);
            return reclamationRepository.save(reclamation);
        } else {
            throw new IllegalStateException("Reclamation cannot be updated as it is not in PENDING state.");
        }
    }


    //@PreAuthorize("hasRole('ADMIN')")

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


    /*

    // ken kif tebda pending
    public Reclamation updateReclamation(Long id, Reclamation newReclamationData) {
        Reclamation reclamation = reclamationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reclamation not found"));

        if (reclamation.getState() == StateReclamation.PENDING) {
            reclamation.setTitle(newReclamationData.getTitle());
            reclamation.setDescription(newReclamationData.getDescription());
            reclamation.setType(newReclamationData.getType());
            reclamation.setDate(newReclamationData.getDate());
            // reclamation.setState(newReclamationData.getState());
            return reclamationRepository.save(reclamation);
        } else {
            throw new IllegalStateException("Reclamation cannot be updated as it is not in PENDING state.");
        }
    }




    public List<Reclamation> getReclamationsForCurrentUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
        return reclamationRepository.findByUser(user);
    }*/

}
