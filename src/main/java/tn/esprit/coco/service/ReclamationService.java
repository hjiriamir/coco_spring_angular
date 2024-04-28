package tn.esprit.coco.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import tn.esprit.coco.entity.Reclamation;
import tn.esprit.coco.entity.Ride;
import tn.esprit.coco.entity.StateReclamation;
import tn.esprit.coco.entity.User;
import tn.esprit.coco.repository.ReclamationRepository;
import tn.esprit.coco.repository.RideRepository;
import tn.esprit.coco.repository.UserRepository;

import java.util.Date;
import java.util.List;

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

        return reclamationRepository.save(reclamation);
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


    public Reclamation updateReclamationState(Long id, StateReclamation newState) {
        Reclamation reclamation = reclamationRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Reclamation not found with id: " + id));

        reclamation.setState(newState);
        return reclamationRepository.save(reclamation);
    }


    public void deleteReclamation(Long id) {
        reclamationRepository.deleteById(id);
    }

    // Get all
    public List<Reclamation> getAllReclamations() {
        return reclamationRepository.findAll();
    }

    public List<Reclamation> getReclamationsForCurrentUser(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email: " + userEmail));
        return reclamationRepository.findByUser(user);
    }*/

}
