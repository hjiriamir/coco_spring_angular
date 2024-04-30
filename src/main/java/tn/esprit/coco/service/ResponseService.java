package tn.esprit.coco.service;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.coco.dto.ResponseDto;
import tn.esprit.coco.entity.ERole;
import tn.esprit.coco.entity.Reclamation;
import tn.esprit.coco.entity.Response;
import tn.esprit.coco.entity.User;
import tn.esprit.coco.repository.ReclamationRepository;
import tn.esprit.coco.repository.ResponseRepository;
import tn.esprit.coco.repository.UserRepository;

import java.nio.file.attribute.UserPrincipal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ResponseService {
    @Autowired
    private ResponseRepository responseRepository;

    @Autowired
    private ReclamationRepository reclamationRepository;
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserDetailsServiceImpl userDetailsService;

    public Response addResponse(Long reclamationId, String message) {
        User admin = userDetailsService.getCurrentUser();
        Reclamation reclamation = reclamationRepository.findById(reclamationId)
                .orElseThrow(() -> new RuntimeException("Reclamation not found with id: " + reclamationId));

        Response response = new Response();
        response.setMessage(message);
        response.setReclamation(reclamation);
        response.setResponder(admin);
        return responseRepository.save(response);
    }

    public List<ResponseDto> getAllResponses() {
        return responseRepository.findAll().stream().map(response -> new ResponseDto(
                response.getId(),
                response.getMessage(),
                response.getReclamation().getTitle(),
                response.getReclamation().getUser().getUsername()
        )).collect(Collectors.toList());
    }

    @Transactional
    public void deleteResponse(Long responseId) {
        Response response = responseRepository.findById(responseId)
                .orElseThrow(() -> new RuntimeException("Response not found with id: " + responseId));
        responseRepository.delete(response);
    }




    public List<Response> getResponsesForReclamation(Long reclamationId) {
        return responseRepository.findByReclamationId(reclamationId);
    }




}
