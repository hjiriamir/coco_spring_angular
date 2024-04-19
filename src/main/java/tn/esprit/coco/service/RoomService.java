package tn.esprit.coco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;
import tn.esprit.coco.entity.Room;
import tn.esprit.coco.entity.RoomPhoto;
import tn.esprit.coco.repository.RoomPhotoRepository;
import tn.esprit.coco.repository.RoomRepository;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.util.ArrayList;
import java.util.Base64;
import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
public class RoomService {

    private final RoomRepository roomRepository;
    private final RoomPhotoRepository roomPhotoRepository;

    @Autowired
    private Environment environment;

    @Autowired
    public RoomService(RoomRepository roomRepository, RoomPhotoRepository roomPhotoRepository) {
        this.roomRepository = roomRepository;
        this.roomPhotoRepository = roomPhotoRepository;
    }

    public Room saveRoom(Room room) {
        return roomRepository.save(room);
    }

    public Optional<Room> getRoomById(Long id) {
        return roomRepository.findById(id);
    }

    public List<Room> getAllRooms() {
        return roomRepository.findAll();
    }

    public void deleteRoomById(Long id) {
        roomRepository.deleteById(id);
    }

    public Room updateRoom(Long id, Room updatedRoom) {
        if (roomRepository.existsById(id)) {
            updatedRoom.setRoomID(id);
            return roomRepository.save(updatedRoom);
        } else {
            return null; // Room with given id not found
        }
    }


    //add image in xamp database

    public List<RoomPhoto> addImagesToRoom(Long id, List<MultipartFile> images) throws IOException {
        Room room = roomRepository.findById(id)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + id));

        List<RoomPhoto> roomPhotos = new ArrayList<>();

        for (MultipartFile image : images) {
            // Generate a unique file name for each image
            String fileName = generateUniqueFileName(image);

            // Save the image file to the specified directory
            Path uploadPath = Paths.get(environment.getProperty("upload.room.images"));
            Files.createDirectories(uploadPath);
            Files.copy(image.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);

            // Create a RoomPhoto object and add it to the list
            RoomPhoto roomPhoto = new RoomPhoto();
            roomPhoto.setFileName(fileName);
            roomPhoto.setRoom(room);
            roomPhotos.add(roomPhoto);
        }

        // Save the list of RoomPhoto objects to the database
        roomPhotoRepository.saveAll(roomPhotos);

        return roomPhotos;
    }
    //add image in xamp database

    public List<byte[]> getImagesByRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + roomId));

        return room.getRoomPhotos().stream()
                .map(photo -> Base64.getDecoder().decode(photo.getImageData()))
                .collect(Collectors.toList());
    }

    // affichage tekhou el path mta3 image from xamp
    public List<String> getImageUrlsForRoom(Long roomId) {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + roomId));

        // Assuming 'path' is the field storing image file names
        List<String> imageUrls = room.getRoomPhotos().stream()
                .map(photo -> constructImageUrl(photo.getFileName()))
                .collect(Collectors.toList());

        return imageUrls;
    }
    private String constructImageUrl(String fileName) {
        String baseUrl = environment.getProperty("export.room.images"); // Change to the appropriate property
        if (baseUrl != null && !baseUrl.isEmpty() && fileName != null && !fileName.isEmpty()) {
            return baseUrl + fileName; // Adjust path if necessary
        } else {
            return null; // Or any default image URL
        }
    }
    // affichage tekhou el path mta3 image from xamp





    public void saveRoomPhotos(List<RoomPhoto> roomPhotos) {
        roomPhotoRepository.saveAll(roomPhotos);
    }
    public Room updateRoomPhoto(Long roomId, List<MultipartFile> roomPhotos) throws IOException {
        Room room = roomRepository.findById(roomId)
                .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + roomId));

        if (roomPhotos != null && !roomPhotos.isEmpty()) {
            String uploadDir = environment.getProperty("upload.room.images");

            // Loop through each uploaded room photo
            for (MultipartFile roomPhoto : roomPhotos) {
                if (roomPhoto != null && !roomPhoto.isEmpty()) {
                    String oldPhotoName = ""; // You need to determine the old photo name here
                    String newPhotoName = generateUniqueFileName(roomPhoto);
                    updateFile(uploadDir, oldPhotoName, newPhotoName, roomPhoto);

                    // Create a new RoomPhoto object for the updated photo
                    RoomPhoto newRoomPhoto = new RoomPhoto();
                    newRoomPhoto.setPath(newPhotoName); // Assuming 'path' is the field storing the photo filename
                    newRoomPhoto.setTitle(newPhotoName); // Assuming 'title' should be the same as the filename
                    newRoomPhoto.setRoom(room);

                    // Add the new RoomPhoto object to the list
                    room.getRoomPhotos().add(newRoomPhoto);
                }
            }

            // Save the updated room
            roomRepository.save(room);
        }

        return room;
    }

    private String generateUniqueFileName(MultipartFile file) {
        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
        return fileName;
    }

    private void updateFile(String uploadDir, String oldFileName, String newFileName, MultipartFile multipartFile)
            throws IOException {
        if (oldFileName != null) {
            Files.deleteIfExists(Paths.get(uploadDir + "/" + oldFileName));
        }
        Files.copy(multipartFile.getInputStream(), Paths.get(uploadDir + "/" + newFileName),
                StandardCopyOption.REPLACE_EXISTING);
    }
}


//package tn.esprit.coco.service;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//import org.springframework.util.StringUtils;
//import org.springframework.web.multipart.MultipartFile;
//import tn.esprit.coco.entity.Accommodation;
//import tn.esprit.coco.entity.ImageUtil;
//import tn.esprit.coco.entity.Room;
//import tn.esprit.coco.entity.RoomPhoto;
//import tn.esprit.coco.repository.AccommodationRepository;
//import tn.esprit.coco.repository.RoomRepository;
//import org.springframework.core.env.Environment;
//import tn.esprit.coco.repository.RoomPhotoRepository;
//
//
//import java.io.IOException;
//import java.nio.file.Files;
//import java.nio.file.Path;
//import java.nio.file.Paths;
//import java.nio.file.StandardCopyOption;
//import java.util.*;
//import java.util.stream.Collectors;
//
//@Service
//public class RoomService {
//
//    //image part variable :
//    private final RoomPhotoRepository roomPhotoRepository;
//
//    @Autowired
//    private Environment environment;
//
//    @Autowired
//    public RoomService(RoomRepository roomRepository, AccommodationRepository accommodationRepository, RoomPhotoRepository roomPhotoRepository) {
//        this.roomRepository = roomRepository;
//        this.accommodationRepository = accommodationRepository;
//        this.roomPhotoRepository = roomPhotoRepository;
//    }
//
//    private final RoomRepository roomRepository;
//    private final AccommodationRepository accommodationRepository;
//
////    @Autowired
////    public RoomService(RoomRepository roomRepository, AccommodationRepository accommodationRepository) {
////        this.roomRepository = roomRepository;
////        this.accommodationRepository = accommodationRepository;
////
////    }
//
//    public Room saveRoom(Room room) {
//        return roomRepository.save(room);
//    }
//    public Optional<String> getAccommodationNameByRoomId(Long roomId) {
//        Optional<Room> roomOptional = roomRepository.findById(roomId);
//
//        return roomOptional.flatMap(room -> {
//            Accommodation accommodation = room.getAccommodations(); // Utilisation du champ accommodations au lieu de accommodation
//            if (accommodation != null) {
//                return Optional.of(accommodation.getAccommodationName());
//            } else {
//                return Optional.empty(); // Return empty if no accommodation is associated with the room
//            }
//        });
//    }
//    public Optional<Room> getRoomById(Long id) {
//        return roomRepository.findById(id);
//    }
//
//    public List<Room> getAllRooms() {
//        return roomRepository.findAll();
//    }
//
//    public void deleteRoomById(Long id) {
//        roomRepository.deleteById(id);
//    }
//
//    public Room updateRoom(Long id, Room updatedRoom) {
//        if (roomRepository.existsById(id)) {
//            updatedRoom.setRoomID(id);
//            return roomRepository.save(updatedRoom);
//        } else {
//            return null; // Room with given id not found
//        }
//    }
//
////    public List<String> addImagesToRoom(Long id, List<MultipartFile> images) throws IOException {
////        Room room = roomRepository.findById(id).orElse(null);
////        List<String> imagePaths = new ArrayList<>();
////
////        if (room != null) {
////            for (MultipartFile image : images) {
////                String imageName = StringUtils.cleanPath(image.getOriginalFilename());
////                String imageExtension = StringUtils.getFilenameExtension(imageName);
////                String generatedImageName = UUID.randomUUID().toString() + "." + imageExtension;
////
////                Path uploadsDir = Paths.get("src/main/resources/images");
////                Path imagePath = uploadsDir.resolve(generatedImageName);
////                Files.copy(image.getInputStream(), imagePath, StandardCopyOption.REPLACE_EXISTING);
////
////                // Stocker uniquement le chemin relatif
////                imagePaths.add("images/" + generatedImageName);
////            }
////
////            room.getImagePaths().addAll(imagePaths);
////            roomRepository.save(room);
////
////            return imagePaths;
////        } else {
////            return null; // Si la chambre n'est pas trouvée
////        }
////    }
//
//    public List<byte[]> getImagesByRoom(String roomId) throws IOException {
//        // Chemin du répertoire contenant les images
//        Path directoryPath = Paths.get("src/main/resources/images");
//
//        // Recherche des images pour la chambre spécifiée
//        List<byte[]> imagesData = Files.walk(directoryPath)
//                .filter(path -> path.getFileName().toString().startsWith(roomId))
//                .map(this::readImageData)
//                .toList();
//
//        return imagesData;
//    }
//
//    private byte[] readImageData(Path imagePath) {
//        try {
//            return Files.readAllBytes(imagePath);
//        } catch (IOException e) {
//            e.printStackTrace(); // Gérer l'exception correctement selon votre cas
//            return null;
//        }
//    }
//
//    //*****************************************image part******************************************//:
//    public List<RoomPhoto> addImagesToRoom(Long id, List<MultipartFile> images) throws IOException {
//        Room room = roomRepository.findById(id)
//                .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + id));
//
//        List<RoomPhoto> roomPhotos = new ArrayList<>();
//
//        for (MultipartFile image : images) {
//            // Generate a unique file name for each image
//            String fileName = generateUniqueFileName(image);
//
//            // Save the image file to the specified directory
//            Path uploadPath = Paths.get(environment.getProperty("upload.room.images"));
//            Files.createDirectories(uploadPath);
//            Files.copy(image.getInputStream(), uploadPath.resolve(fileName), StandardCopyOption.REPLACE_EXISTING);
//
//            // Create a RoomPhoto object and add it to the list
//            RoomPhoto roomPhoto = new RoomPhoto();
//            roomPhoto.setFileName(fileName);
//            roomPhoto.setRoom(room);
//            roomPhotos.add(roomPhoto);
//        }
//
//        // Save the list of RoomPhoto objects to the database
//        roomPhotoRepository.saveAll(roomPhotos);
//
//        return roomPhotos;
//    }
//
//    private String generateUniqueFileName(MultipartFile file) {
//        String originalFileName = StringUtils.cleanPath(file.getOriginalFilename());
//        String fileName = UUID.randomUUID().toString() + "_" + originalFileName;
//        return fileName;
//    }
//    // affichage tekhou el path mta3 image from xamp
//    public List<String> getImageUrlsForRoom(Long roomId) {
//        Room room = roomRepository.findById(roomId)
//                .orElseThrow(() -> new IllegalArgumentException("Room not found with ID: " + roomId));
//
//        // Assuming 'path' is the field storing image file names
//        List<String> imageUrls = room.getRoomPhotos().stream()
//                .map(photo -> constructImageUrl(photo.getFileName()))
//                .collect(Collectors.toList());
//
//        return imageUrls;
//    }
//    private String constructImageUrl(String fileName) {
//        String baseUrl = environment.getProperty("export.room.images"); // Change to the appropriate property
//        if (baseUrl != null && !baseUrl.isEmpty() && fileName != null && !fileName.isEmpty()) {
//            return baseUrl + fileName; // Adjust path if necessary
//        } else {
//            return null; // Or any default image URL
//        }
//    }
//    // affichage tekhou el path mta3 image from xamp
//
//}
