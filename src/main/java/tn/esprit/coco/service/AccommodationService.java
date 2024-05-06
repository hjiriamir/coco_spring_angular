package tn.esprit.coco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.coco.entity.Accommodation;
import tn.esprit.coco.entity.Category;
import tn.esprit.coco.repository.AccommodationRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class AccommodationService {

    private final AccommodationRepository accommodationRepository;

    @Autowired
    public AccommodationService(AccommodationRepository accommodationRepository) {
        this.accommodationRepository = accommodationRepository;
    }

    public Accommodation saveAccommodation(Accommodation accommodation) {
       /* System.out.println("SAVED ACCOMANDATION : " + accommodation);*/

        return accommodationRepository.save(accommodation);
    }

    public Optional<Accommodation> getAccommodationById(Long id) {
        return accommodationRepository.findById(id);
    }

    public List<Accommodation> getAllAccommodations() {
        return accommodationRepository.findAll();
    }

    public void deleteAccommodationById(Long id) {
        accommodationRepository.deleteById(id);
    }

    public Accommodation updateAccommodation(Long id, Accommodation updatedAccommodation) {
        if (accommodationRepository.existsById(id)) {
            updatedAccommodation.setAccommodationID(id);
            return accommodationRepository.save(updatedAccommodation);
        } else {
            return null;
        }
    }
    public Accommodation addCategoryToAccommodation(Long accommodationId, Category category) {
        Optional<Accommodation> accommodationOptional = accommodationRepository.findById(accommodationId);
        if (accommodationOptional.isPresent()) {
            Accommodation accommodation = accommodationOptional.get();
            accommodation.setCategory(category);

            return accommodationRepository.save(accommodation);
        } else {
            return null;
        }
    }
    public Optional<Accommodation> getAccommodationLocationById(Long accommodationId) {
        return accommodationRepository.findById(accommodationId);
    }
    public Optional<Accommodation> getAccommodationByIdWithCategory(Long id) {
        return accommodationRepository.findAccommodationByIdWithCategory(id);
    }
    public Accommodation addImageToAcc(Long accomodationId, MultipartFile image) throws IOException {
        Accommodation accommodation = accommodationRepository.findById(accomodationId).orElse(null);
        if (accommodation != null) {
            String imageName = StringUtils.cleanPath(image.getOriginalFilename());
            String imageExtension = StringUtils.getFilenameExtension(imageName);
            String generatedImageName = UUID.randomUUID().toString() + "." + imageExtension;

            Path imagePath = Paths.get("uploads").resolve(generatedImageName);
            Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);

            accommodation.setImageName(imageName);
            accommodation.setImagePath(imagePath.toString());

            accommodationRepository.save(accommodation);
        }
        return accommodation;
    }
    public byte[] getImageForAcc(Long accomodationId) throws IOException {
        Accommodation accommodation = accommodationRepository.findById(accomodationId).orElse(null);
        if (accommodation != null && accommodation.getImagePath() != null) {
            Path imagePath = Paths.get(accommodation.getImagePath());
            return Files.readAllBytes(imagePath);
        } else {
            throw new IOException("Image not found for accomodation with ID: " + accomodationId);
        }
    }
}
