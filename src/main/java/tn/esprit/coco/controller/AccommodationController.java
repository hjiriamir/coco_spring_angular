package tn.esprit.coco.controller;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.coco.entity.Accommodation;
import tn.esprit.coco.entity.Category;
import tn.esprit.coco.entity.Room;
import tn.esprit.coco.service.AccommodationService;
import tn.esprit.coco.service.CategoryService;
import tn.esprit.coco.service.RoomService;

import java.io.IOException;
import java.util.List;
import java.util.Optional;

@RestController
//@CrossOrigin("*")
@CrossOrigin(origins = "http://localhost:4200",methods={RequestMethod.POST,RequestMethod.PUT, RequestMethod.DELETE,RequestMethod.GET,RequestMethod.PATCH})
@RequestMapping("/api/accommodations")
public class AccommodationController {

    private final AccommodationService accommodationService;
    private final CategoryService categoryService;

    @Autowired
    private RoomService roomService;

    @Autowired
    public AccommodationController(AccommodationService accommodationService,
                                   CategoryService categoryService)
    {
        this.categoryService = categoryService;
        this.accommodationService = accommodationService;
    }

    @GetMapping("/getAllAccomodation")
    public ResponseEntity<List<Accommodation>> getAllAccommodations() {
        List<Accommodation> accommodations = accommodationService.getAllAccommodations();
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping("/getAccomodationById/{id}")
    public ResponseEntity<Accommodation> getAccommodationById(@PathVariable Long id) {
        Optional<Accommodation> accommodation = accommodationService.getAccommodationById(id);
        return accommodation.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addAccomodation/{categoryId}")
    public ResponseEntity<Accommodation> addAccommodation(@RequestBody Accommodation accommodation, @PathVariable Long categoryId) {
        Optional<Category> categoryOptional = categoryService.getCategoryById(categoryId);
        System.out.println("Category Optional: " + categoryOptional);
        System.out.println("Category Optional: " + accommodation);

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            accommodation.setCategory(category);
            accommodation.setCategoryTitle(category.getCategoryTitle());
            System.out.println("ACC AFTER SET CATEGORY : " + accommodation);

            Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation);
            System.out.println("SAVED ACCOMANDATION : " + savedAccommodation);

            return new ResponseEntity<>(savedAccommodation, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/{accomodationId}/image")
    public ResponseEntity<Accommodation> addImageToAcc(@PathVariable Long accomodationId, @RequestParam("image") MultipartFile image) {
        try {
            Accommodation accommodation = accommodationService.addImageToAcc(accomodationId, image);
            return new ResponseEntity<>(accommodation, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    @GetMapping("/{accomodationId}/image")
    public ResponseEntity<byte[]> getImageForAcc(@PathVariable Long accomodationId) {
        try {
            byte[] imageData = accommodationService.getImageForAcc(accomodationId);
            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.IMAGE_JPEG);
            return new ResponseEntity<>(imageData, headers, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PutMapping("update/{id}")
    public ResponseEntity<Accommodation> updateAccommodation(@PathVariable Long id, @RequestBody Accommodation accommodation) {
        Accommodation updatedAccommodation = accommodationService.updateAccommodation(id, accommodation);
        if (updatedAccommodation != null) {
            return new ResponseEntity<>(updatedAccommodation, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @DeleteMapping("deleteAccomodation/{id}")
    public ResponseEntity<Void> deleteAccommodation(@PathVariable Long id) {
        accommodationService.deleteAccommodationById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
    @PostMapping("/addRoomToAccommodation")
    public ResponseEntity<Room> addRoomToAccommodation(@RequestBody Room room, @RequestParam Long accommodationId) {
        Optional<Accommodation> accommodationOptional = accommodationService.getAccommodationById(accommodationId);

        if (accommodationOptional.isPresent()) {
            Accommodation accommodation = accommodationOptional.get();

            room.setAccommodations(accommodation);

            Room savedRoom = roomService.saveRoom(room);

            accommodation.getRooms().add(savedRoom);
            accommodationService.saveAccommodation(accommodation);

            return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{accommodationId}/removeRoom/{roomId}")
    public ResponseEntity<Void> removeRoomFromAccommodation(@PathVariable Long accommodationId, @PathVariable Long roomId) {
        Optional<Accommodation> accommodationOptional = accommodationService.getAccommodationById(accommodationId);

        if (accommodationOptional.isPresent()) {
            Accommodation accommodation = accommodationOptional.get();

            Optional<Room> roomOptional = accommodation.getRooms().stream()
                    .filter(room -> room.getRoomID().equals(roomId))
                    .findFirst();

            if (roomOptional.isPresent()) {
                Room room = roomOptional.get();

                accommodation.getRooms().remove(room);

                accommodationService.saveAccommodation(accommodation);

                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            } else {
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("getLocation/{id}")
    public ResponseEntity<Accommodation> getAccommodationByIdWithLocation(@PathVariable Long id) {
        Optional<Accommodation> accommodationOptional = accommodationService.getAccommodationLocationById(id);
        return accommodationOptional.map(accommodation -> new ResponseEntity<>(accommodation, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }


    @PostMapping("/addCategoryToAccommodation")
    public ResponseEntity<Accommodation> addCategoryToAccommodation(@RequestBody Accommodation accommodation, @RequestParam Long categoryId) {
        Optional<Category> categoryOptional = categoryService.getCategoryById(categoryId);

        if (categoryOptional.isPresent()) {
            Category category = categoryOptional.get();
            accommodation.getCategories().add(category);

            Accommodation savedAccommodation = accommodationService.saveAccommodation(accommodation);
            return new ResponseEntity<>(savedAccommodation, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("getAccomodationByIdWithCategory/{id}")
    public ResponseEntity<Accommodation> getAccommodationByIdWithCategory(@PathVariable Long id) {
        Optional<Accommodation> accommodation = accommodationService.getAccommodationByIdWithCategory(id);
        return accommodation.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
}
