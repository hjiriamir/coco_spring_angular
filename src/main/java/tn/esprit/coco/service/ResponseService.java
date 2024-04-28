package tn.esprit.coco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.coco.entity.ERole;
import tn.esprit.coco.entity.Reclamation;
import tn.esprit.coco.entity.Response;
import tn.esprit.coco.entity.User;
import tn.esprit.coco.repository.ReclamationRepository;
import tn.esprit.coco.repository.ResponseRepository;
import tn.esprit.coco.repository.UserRepository;

import java.util.List;

@Service
public class ResponseService {
    /*@Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private ReclamationRepository reclamationRepository;
    @Autowired
    private UserRepository userRepository;

    public Response addResponse(Long reclamationId, String message, String userEmail) {
        User admin = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (admin.getRoles().stream().noneMatch(role -> role.getName().equals(ERole.ADMIN))) {
            throw new IllegalStateException("Only users with the admin role can respond to reclamations");
        }

        Reclamation reclamation = reclamationRepository.findById(reclamationId)
                .orElseThrow(() -> new IllegalArgumentException("Reclamation not found"));

        Response response = new Response();
        response.setMessage(message);
        response.setAdmin(admin);
        response.setReclamation(reclamation);
        response.setRecipient(reclamation.getUser()); // Set the user who made the reclamation as the recipient of the response

        return responseRepository.save(response);
    }





    public List<Response> getAllResponses(String userEmail) {
        User user = userRepository.findByEmail(userEmail)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        if (!user.getRoles().stream().anyMatch(role -> role.getName().equals(ERole.ADMIN))) {
            throw new IllegalStateException("Only admin users can view all responses");
        }

        return responseRepository.findAll();
    }*/

}
