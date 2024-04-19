package tn.esprit.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.entity.Accommodation;
import tn.esprit.coco.entity.Room;
import tn.esprit.coco.service.AccommodationService;
import tn.esprit.coco.service.RoomService;

import java.util.List;
import java.util.Optional;

@RestController
@CrossOrigin("*")
@RequestMapping("/api/rooms")
public class RoomController {

    private final RoomService roomService;
    private final AccommodationService accommodationService;

    @Autowired
    public RoomController(RoomService roomService, AccommodationService accommodationService) {
        this.roomService = roomService;
        this.accommodationService = accommodationService;
    }

    @GetMapping("/getAccommodations")
    public ResponseEntity<List<Accommodation>> getAccommodations() {
        List<Accommodation> accommodations = accommodationService.getAllAccommodations();
        return new ResponseEntity<>(accommodations, HttpStatus.OK);
    }

    @GetMapping("/getAllRoom")
    public ResponseEntity<List<Room>> getAllRooms() {
        List<Room> rooms = roomService.getAllRooms();
        return new ResponseEntity<>(rooms, HttpStatus.OK);
    }

    @GetMapping("getRoomById/{id}")
    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
        Optional<Room> room = roomService.getRoomById(id);
        return room.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @PostMapping("/addRoom")
    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
        Room savedRoom = roomService.saveRoom(room);
        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
    }

    @PutMapping("updateRoom/{id}")
    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
        Room updatedRoom = roomService.updateRoom(id, room);
        if (updatedRoom != null) {
            return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("deleteRoom/{id}")
    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
        roomService.deleteRoomById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PostMapping("/addRoomToAccommodation/{accommodationId}")
    public ResponseEntity<Room> addRoomToAccommodation(@RequestBody Room room, @PathVariable Long accommodationId) {
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

    /*@PostMapping("/{id}/updatePhoto")
    public ResponseEntity<?> updateRoomPhoto(@PathVariable Long id, @RequestParam("photo") List<MultipartFile> roomPhotos) {
        try {
            Room updatedRoom = roomService.updateRoomPhoto(id, roomPhotos);
            if (updatedRoom != null) {
                return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
            } else {
                return new ResponseEntity<>("Room not found with ID: " + id, HttpStatus.NOT_FOUND);
            }
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to update room photo: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/addImages/{id}/images")
    public ResponseEntity<?> addImagesToRoom(@PathVariable Long id, @RequestParam("images") List<MultipartFile> images) {
        try {
            List<RoomPhoto> roomPhotos = roomService.addImagesToRoom(id, images);
            return new ResponseEntity<>(roomPhotos, HttpStatus.OK);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to upload images: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @GetMapping("/{id}/images")
    public ResponseEntity<List<String>> getImagesForRoom(@PathVariable Long id) {
        List<String> imageUrls = roomService.getImageUrlsForRoom(id);
        return ResponseEntity.ok(imageUrls);
    }*/
}




//package tn.esprit.coco.controller;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.http.HttpStatus;
//import org.springframework.http.ResponseEntity;
//import org.springframework.web.bind.annotation.*;
//import org.springframework.web.multipart.MultipartFile;
//import tn.esprit.coco.entity.Accommodation;
//import tn.esprit.coco.entity.Room;
//import tn.esprit.coco.entity.RoomPhoto;
//import tn.esprit.coco.service.AccommodationService;
//import tn.esprit.coco.service.RoomService;
//
//import java.io.IOException;
//import java.util.List;
//import java.util.Optional;
//
//@RestController
//@CrossOrigin("*")
//@RequestMapping("/api/rooms")
//public class RoomController {
//
//    private final RoomService roomService;
//    private final AccommodationService accommodationService;
//
//    @Autowired
//    public RoomController(RoomService roomService, AccommodationService accommodationService) {
//        this.roomService = roomService;
//        this.accommodationService = accommodationService;
//    }
//
//    @GetMapping("/getAccommodations")
//    public ResponseEntity<List<Accommodation>> getAccommodations() {
//        List<Accommodation> accommodations = accommodationService.getAllAccommodations();
//        return new ResponseEntity<>(accommodations, HttpStatus.OK);
//    }
//
//    @GetMapping("/getAllRoom")
//    public ResponseEntity<List<Room>> getAllRooms() {
//        List<Room> rooms = roomService.getAllRooms();
//        return new ResponseEntity<>(rooms, HttpStatus.OK);
//    }
//
//    @GetMapping("getRoomById/{id}")
//    public ResponseEntity<Room> getRoomById(@PathVariable Long id) {
//        Optional<Room> room = roomService.getRoomById(id);
//        return room.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//
//    @PostMapping("/addRoom")
//    public ResponseEntity<Room> addRoom(@RequestBody Room room) {
//        Room savedRoom = roomService.saveRoom(room);
//        return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
//    }
//
//    @PutMapping("updateRoom/{id}")
//    public ResponseEntity<Room> updateRoom(@PathVariable Long id, @RequestBody Room room) {
//        Room updatedRoom = roomService.updateRoom(id, room);
//        if (updatedRoom != null) {
//            return new ResponseEntity<>(updatedRoom, HttpStatus.OK);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
//    @DeleteMapping("deleteRoom/{id}")
//    public ResponseEntity<Void> deleteRoom(@PathVariable Long id) {
//        roomService.deleteRoomById(id);
//        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
//    }
//
//    @PostMapping("/addRoomToAccommodation/{accommodationId}")
//    public ResponseEntity<Room> addRoomToAccommodation(@RequestBody Room room, @PathVariable Long accommodationId) {
//        Optional<Accommodation> accommodationOptional = accommodationService.getAccommodationById(accommodationId);
//
//        if (accommodationOptional.isPresent()) {
//            Accommodation accommodation = accommodationOptional.get();
//
//            room.setAccommodations(accommodation);
//
//            Room savedRoom = roomService.saveRoom(room);
//
//            accommodation.getRooms().add(savedRoom);
//            accommodationService.saveAccommodation(accommodation);
//
//            return new ResponseEntity<>(savedRoom, HttpStatus.CREATED);
//        } else {
//            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
//        }
//    }
//
////    @PostMapping("/addImages/{id}/images")
////    public ResponseEntity<?> addImagesToRoom(@PathVariable Long id, @RequestParam("images") List<MultipartFile> images) {
////        try {
////            List<String> imagePaths = roomService.addImagesToRoom(id, images);
////            if (imagePaths != null && !imagePaths.isEmpty()) {
////                return new ResponseEntity<>(imagePaths, HttpStatus.OK);
////            } else {
////                return new ResponseEntity<>("Room not found with ID: " + id, HttpStatus.NOT_FOUND);
////            }
////        } catch (IOException e) {
////            return new ResponseEntity<>("Failed to upload images: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
////        }
////    }
//
//    //**********************************image part**************************//
//    @PostMapping("/addImages/{id}/images")
//    public ResponseEntity<?> addImagesToRoom(@PathVariable Long id, @RequestParam("images") List<MultipartFile> images) {
//        try {
//            List<RoomPhoto> roomPhotos = roomService.addImagesToRoom(id, images);
//            return new ResponseEntity<>(roomPhotos, HttpStatus.OK);
//        } catch (IOException e) {
//            return new ResponseEntity<>("Failed to upload images: " + e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
//        }
//    }
//
//    @GetMapping("/get/{id}/images")
//    public ResponseEntity<List<String>> getImagesForRoom(@PathVariable Long id) {
//        List<String> imageUrls = roomService.getImageUrlsForRoom(id);
//        return ResponseEntity.ok(imageUrls);
//    }
//    //**********************************image part**************************//
//
//
//    @GetMapping("/{id}/images")
//    public List<byte[]> getImagesForRoomAsBase64(@PathVariable Long id) throws IOException {
//        return roomService.getImagesByRoom(String.valueOf(id));
//    }
//    @GetMapping("/{id}/accommodationName")
//    public ResponseEntity<String> getAccommodationNameById(@PathVariable Long id) {
//        Optional<String> accommodationNameOptional = roomService.getAccommodationNameByRoomId(id);
//        return accommodationNameOptional.map(name -> new ResponseEntity<>(name, HttpStatus.OK))
//                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
//    }
//}
