package tn.esprit.coco.controller;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.entity.*;
import tn.esprit.coco.repository.*;
import tn.esprit.coco.service.CloudinarySrvice;
import tn.esprit.coco.service.ICovService;
import tn.esprit.coco.serviceImp.ImageService;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

@RestController
@RequiredArgsConstructor
//@RequestMapping("/cloudinary")
@AllArgsConstructor
@CrossOrigin(origins = "http://localhost:4200",methods={RequestMethod.POST,RequestMethod.PUT,
        RequestMethod.DELETE,RequestMethod.GET,RequestMethod.PATCH})
public class CovController {
    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private SecurityRepository securityRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private ICovService icovService;
    @Autowired
   CloudinarySrvice cloudinaryService;

    @Autowired
    ImageService imageService;

/* * */

    /**/
    @PostMapping("/addRide")
    public ResponseEntity<Ride> addRide(@RequestBody Ride ride) {
       /* Ride rideobj = rideRepository.save(ride);
        return  new ResponseEntity<>(rideobj, HttpStatus.OK);*/
        Ride addedRide = icovService.addRide(ride);
        return new ResponseEntity<>(addedRide, HttpStatus.OK);
    }

    @PatchMapping ("/updateRide/{rideID}")
    public ResponseEntity<Ride> updateRide(@PathVariable Long rideID, @RequestBody Ride ride) {

        Ride updatedRideObject = icovService.updateRide(rideID, ride);
        if (updatedRideObject != null) {
            return new ResponseEntity<>(updatedRideObject, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteRide/{rideID}")
    public void deleteRide(@PathVariable Long rideID) {
        icovService.deleteRide(rideID);
    }

   /* @GetMapping("/getRideById/{rideID}")
    public ResponseEntity<Ride> getRideById(@PathVariable Long rideID) {
        return icovService.getRideById(rideID);
    }*/
   @GetMapping("/rides")
   public List<Ride> getRideByStartlocation(@RequestParam("startlocation") String startlocation) {
       return icovService.getRideByStartlocation(startlocation);
   }
   @GetMapping("/getRideById/{rideID}")
   public ResponseEntity<Ride> getRideById(@PathVariable Long rideID) {
       Optional<Ride> ride = icovService.getRideById(rideID);
       return ride.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
               .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
   }
    @GetMapping("/getAllAdress")
    public List<String> getAllAdress() {
        return icovService.getAddress();
    }
    /*@GetMapping("/getAllRide")
    public ResponseEntity<List<Ride>> getAllRide() {
        return icovService.getAllRide();
    }*/
   /* @GetMapping("/getAllRide")
    public List<Ride> getAllRide() {*/
        /* if (rideL.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rideL, HttpStatus.OK);*/
       // return icovService.getAllRide();
       /* return rideRepository.findAll();

    }*/
   /* @GetMapping("/getAllRide")
    public List<Ride> getAllRide() {
        return icovService.getAllRide();
    }
    @GetMapping
    public ResponseEntity<List<Ride>> getAllRides() {
        return ResponseEntity.ok(rideRepository.findAll());
    }*/
  /* @GetMapping("/getAlRide")
   public ResponseEntity<List<Ride>> getAllRides() {
       List<Ride> rideList = icovService.findAllRides();
       if (rideList.isEmpty()) {
           return new ResponseEntity<>(HttpStatus.NO_CONTENT);
       }
       return new ResponseEntity<>(rideList, HttpStatus.OK);
   }*/

    @GetMapping("/getAllRides")
    public ResponseEntity<List<Ride>> getAllRieds() {
        List<Ride> rides = icovService.getAllRide();
        if (rides.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(rides, HttpStatus.OK);
    }





    /****************************** Car Controller *************************************/
    @PostMapping("/addCar")
    public ResponseEntity<Car> addCar(@RequestBody Car car) {
        Car savedCar = icovService.addCar(car);
        return new ResponseEntity<>(savedCar, HttpStatus.CREATED);
    }

    @GetMapping("getCarById/{carId}")
    public ResponseEntity<Car> getCarById(@PathVariable Long carId) {
        Optional<Car> carOptional = icovService.findCarById(carId);
        return carOptional.map(car -> new ResponseEntity<>(car, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("getAllCar")
    public ResponseEntity<List<Car>> getAllCars() {
        List<Car> carList = icovService.findAllCars();
        if (carList.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(carList, HttpStatus.OK);
    }

   /* @PutMapping("/updateCar/{carID}")
    public ResponseEntity<Car> updateCar(@PathVariable Long carID, @RequestBody Car car) {
        Car updatedCar = icovService.updateCar(carID, car);
        if (updatedCar != null) {
            return new ResponseEntity<>(updatedCar, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);}*/
   @PatchMapping("/updateCar/{carID}")
   public ResponseEntity<Car> updateCar(@PathVariable Long carID, @RequestBody Car car) {
       Car updatedCar = icovService.updateCar(carID, car);
       if (updatedCar != null) {
           return new ResponseEntity<>(updatedCar, HttpStatus.OK);
       }
       return new ResponseEntity<>(HttpStatus.NOT_FOUND);
   }
    @PutMapping("/edit/{carID}")
    public ResponseEntity<Car> editCar(@PathVariable Long carID, @RequestBody Car car) {
        Optional<Car> editedCar = icovService.editCar(carID, car);
        return editedCar.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }
    @DeleteMapping("/deleteCar/{carId}")
    public ResponseEntity<HttpStatus> deleteCar(@PathVariable Long carId) {
        icovService.deleteCar(carId);
        return new ResponseEntity<>(HttpStatus.OK);
    }
    @PostMapping("/affectCarToRide")
    public ResponseEntity<String> affectCarToRide(@RequestParam Long carID, @RequestParam Long rideID) {
        try {
            icovService.affectCarToRide(carID, rideID);

            return ResponseEntity.ok("Car affected to ride successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    @GetMapping("/cars/{rideID}")
    public Car getCarByRideID(@PathVariable Long rideID) {
        return icovService.getCarByRideID(rideID);
    }
    @GetMapping("/carse")
    public Car getCarByModel(@RequestParam String model) {
        return icovService.getCarByModel(model);
    }
   /* @PostMapping("/addCar")
    public ResponseEntity<Car> addCar ( @RequestBody Car car){
        Car carobj = carRepository.save(car);
        return  new ResponseEntity<>(carobj, HttpStatus.OK);
    }
    @PostMapping("/updateCar/{carID}")
    public ResponseEntity<Car> updateCar(@PathVariable Long carID, @RequestBody Car car) {

        //covService.updateRide(ride);
        Optional<Car> oldCar=carRepository.findById(carID);
        if (oldCar.isPresent()){
            Car updatedCar=oldCar.get();
            updatedCar.setComfort(car.getComfort());
            updatedCar.setRide(car.getRide());
            updatedCar.setModel(car.getModel());
            updatedCar.setImages(car.getImages());


            Car carobj= carRepository.save(updatedCar);
            return  new ResponseEntity<>(carobj, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/deleteCar/{carID}")
    public ResponseEntity<HttpStatus> deleteCar(@PathVariable Long carID) {
        carRepository.deleteById(carID);
        return  new ResponseEntity<>( HttpStatus.OK);
    }
    @GetMapping("getCarById/{carID}")
    public ResponseEntity<Car> getCarById(@PathVariable Long carID) {
        Optional<Car> R1=carRepository.findById(carID);
        return R1.map(car -> new ResponseEntity<>(car, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/getAllCar")
    public ResponseEntity <List<Car>> getAllCar() {
        try {
            List<Car> carList = new ArrayList<>(carRepository.findAll());

            if (carList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(carList, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
    /****************************** Reservation Controller *******************************/
    /*@PostMapping("/addReservation")
    public ResponseEntity<Reservation> addReservation(@RequestBody Reservation reservation) {
        Reservation addedReservation = icovService.addReservation(reservation);
        return new ResponseEntity<>(addedReservation, HttpStatus.OK);
    }*/
    @PostMapping("/addReservation")
    public ResponseEntity<String> addReservation(@RequestParam("passengerId") Long passengerId,
                                                 @RequestParam("rideId") Long rideId) {
        try {
            icovService.addReservation(passengerId, rideId);
            return ResponseEntity.ok("Reservation added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateReservation/{reservationID}")
    public ResponseEntity<Reservation> updateReservation(@PathVariable Long reservationID, @RequestBody Reservation reservation) {
        Reservation updatedReservation = icovService.updateReservation(reservationID, reservation);
        if (updatedReservation != null) {
            return new ResponseEntity<>(updatedReservation, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteReservation/{reservationID}")
    public ResponseEntity<HttpStatus> deleteReservation(@PathVariable Long reservationID) {
        icovService.deleteReservation(reservationID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getReservationById/{reservationID}")
    public ResponseEntity<Reservation> getReservationById(@PathVariable Long reservationID) {
        Optional<Reservation> reservation = icovService.getReservationById(reservationID);
        return reservation.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getAllReservations")
    public ResponseEntity<List<Reservation>> getAllReservations() {
        List<Reservation> reservations = icovService.getAllReservation();
        if (reservations.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(reservations, HttpStatus.OK);
    }

    /****************************** Payment Controller ******************************/
    @PostMapping("/addPayment")
    public ResponseEntity<Payment> addPayment(@RequestBody Payment payment) {
        Payment addedPayment = icovService.addPayment(payment);
        return new ResponseEntity<>(addedPayment, HttpStatus.OK);
    }

    @PutMapping("/updatePayment/{paymentID}")
    public ResponseEntity<Payment> updatePayment(@PathVariable Long paymentID, @RequestBody Payment payment) {
        Payment updatedPayment = icovService.updatePayment(paymentID, payment);
        if (updatedPayment != null) {
            return new ResponseEntity<>(updatedPayment, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deletePayment/{paymentID}")
    public ResponseEntity<HttpStatus> deletePayment(@PathVariable Long paymentID) {
        icovService.deletePayment(paymentID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getPaymentById/{paymentID}")
    public ResponseEntity<Payment> getPaymentById(@PathVariable Long paymentID) {
        Optional<Payment> payment = icovService.getPaymentById(paymentID);
        return payment.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getAllPayments")
    public ResponseEntity<List<Payment>> getAllPayments() {
        List<Payment> payments = icovService.getAllPayment();
        if (payments.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(payments, HttpStatus.OK);
    }


    /****************************** Favorite Controller ******************************/
   /* @PostMapping("/addFavorite")
    public ResponseEntity<Favorite> addFavorite(@RequestBody Favorite favorite) {
        Favorite addedFavorite = icovService.addFavorite(favorite);
        return new ResponseEntity<>(addedFavorite, HttpStatus.OK);
    }*/
    @PostMapping("/addFavorite")
    public ResponseEntity<String> addFavorite(@RequestParam("passengerId") Long passengerId,
                                              @RequestParam("rideId") Long rideId) {
        try {
            icovService.addFavorite(passengerId, rideId);
            return ResponseEntity.ok("Favorite added successfully");
        } catch (IllegalArgumentException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping("/updateFavorite/{favoriteID}")
    public ResponseEntity<Favorite> updateFavorite(@PathVariable Long favoriteID, @RequestBody Favorite favorite) {
        Favorite updatedFavorite = icovService.updateFavorite(favoriteID, favorite);
        if (updatedFavorite != null) {
            return new ResponseEntity<>(updatedFavorite, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteFavorite/{favoriteID}")
    public ResponseEntity<HttpStatus> deleteFavorite(@PathVariable Long favoriteID) {
        icovService.deleteFavorite(favoriteID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getFavoriteById/{favoriteID}")
    public ResponseEntity<Favorite> getFavoriteById(@PathVariable Long favoriteID) {
        Optional<Favorite> favorite = icovService.getFavoriteById(favoriteID);
        return favorite.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getAllFavorites")
    public ResponseEntity<List<Favorite>> getAllFavorites() {
        List<Favorite> favorites = icovService.getAllFavorite();
        if (favorites.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(favorites, HttpStatus.OK);
    }
   /* @GetMapping("/passengers/{userID}/favorites")
    public ResponseEntity<List<Favorite>> getFavoritesByPassenger(@PathVariable Long userID) {
        List<Favorite> favorites = icovService.getFavoritesByPassenger(userID);
        if (!favorites.isEmpty()) {
            return new ResponseEntity<>(favorites, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND); // Si la liste est vide, on renvoie un statut NOT_FOUND
        }
    }*/
  // @ApiOperation(value = "Get the list of favorites for a given passenger", response = Favorite.class, responseContainer = "List")
   @GetMapping("/passengers/{userID}/favorites")
   public List<Favorite> getFavoritesByPassenger(@PathVariable Long userID) {
       List<Favorite> favorites = icovService.getFavoritesByPassenger(userID);
       if (!favorites.isEmpty()) {
           return  favorites;
       } else {
           return Collections.emptyList(); // Si la liste est vide, on renvoie un statut NOT_FOUND
       }
   }

  /*  @PostMapping("/addFavorite")
    public ResponseEntity<Favorite> addFavorite (@RequestBody Favorite favorite){
        Favorite carobj = favoriteRepository.save(favorite);
        return  new ResponseEntity<>(carobj, HttpStatus.OK);
    }
    @PostMapping("/updateFavorite/{favoriteID}")
    public ResponseEntity<Favorite> updateFavorite(@PathVariable Long favoriteID, @RequestBody Favorite favorite) {

        //covService.updateRide(ride);
        Optional<Favorite> oldFavorite=favoriteRepository.findById(favoriteID);
        if (oldFavorite.isPresent()){
            Favorite updatedFavorite=oldFavorite.get();
            updatedFavorite.setRide(favorite.getRide());
            updatedFavorite.setUser(favorite.getUser());

            Favorite carobj= favoriteRepository.save(updatedFavorite);
            return  new ResponseEntity<>(carobj, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/deleteFavorite/{favoriteID}")
    public ResponseEntity<HttpStatus> deleteFavorite(@PathVariable Long favoriteID) {
        favoriteRepository.deleteById(favoriteID);
        return  new ResponseEntity<>( HttpStatus.OK);
    }
    @GetMapping("getFavoriteById/{favoriteID}")
    public ResponseEntity<Favorite> getFavoriteById(@PathVariable Long favoriteID) {
        Optional<Favorite> R1=favoriteRepository.findById(favoriteID);
        return R1.map(favorite -> new ResponseEntity<>(favorite, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/getAllFavorite")
    public ResponseEntity <List<Favorite>> getAllFavorite() {
        try {
            List<Favorite> favoriteList = new ArrayList<>(favoriteRepository.findAll());

            if (favoriteList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(favoriteList, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
    /****************************** Image Controller ******************************/
    @PostMapping("/addImage")
    public ResponseEntity<Image> addImage(@RequestBody Image image) {
        Image addedImage = icovService.addImage(image);
        return new ResponseEntity<>(addedImage, HttpStatus.OK);
    }

    @PatchMapping("/updateImage/{imageID}")
    public ResponseEntity<Image> updateImage(@PathVariable Long imageID, @RequestBody Image image) {
        Image updatedImage = icovService.updateImage(imageID, image);
        if (updatedImage != null) {
            return new ResponseEntity<>(updatedImage, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteImage/{imageID}")
    public ResponseEntity<HttpStatus> deleteImage(@PathVariable Long imageID) {
        icovService.deleteImage(imageID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getImageById/{imageID}")
    public ResponseEntity<Image> getImageById(@PathVariable Long imageID) {
        Optional<Image> image = icovService.getImageById(imageID);
        return image.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getAllImages")
    public ResponseEntity<List<Image>> getAllImages() {
        List<Image> images = icovService.getAllImage();
        if (images.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(images, HttpStatus.OK);
    }
    @PostMapping("/affectImageToCar")
    public ResponseEntity<String> affectImageToCar(@RequestParam Long imageID, @RequestParam Long carID) {
        try {
            icovService.affectImageToCar(imageID, carID);

            return ResponseEntity.ok("Image affected to car successfully");

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error: " + e.getMessage());
        }
    }

    /*   cloudinary*/
    /*@GetMapping("/list")
    public ResponseEntity<List<Imagee>> list(){
        List<Imagee> list = imageService.list();
        return new ResponseEntity<>(list, HttpStatus.OK);
    }

    @PostMapping("/upload")
    @ResponseBody
    public ResponseEntity<String> upload(@RequestParam MultipartFile multipartFile) throws IOException {
        BufferedImage bi = ImageIO.read(multipartFile.getInputStream());
        if (bi == null) {
            return new ResponseEntity<>("Image non valide!", HttpStatus.BAD_REQUEST);
        }
        Map result = cloudinaryService.upload(multipartFile);
        Imagee image = new Imagee((String) result.get("original_filename"),
                (String) result.get("url"),
                (String) result.get("public_id"));
        imageService.save(image);
        return new ResponseEntity<>("image ajoutée avec succès ! ", HttpStatus.OK);
    }

    @DeleteMapping("/delete/{id}")
    public ResponseEntity<String> delete(@PathVariable("id") int id) {
        Optional<Imagee> imageOptional = imageService.getOne(id);
        if (imageOptional.isEmpty()) {
            return new ResponseEntity<>("n'existe pas", HttpStatus.NOT_FOUND);
        }
        Imagee image = imageOptional.get();
        String cloudinaryImageId = image.getImageId();
        try {
            cloudinaryService.delete(cloudinaryImageId);
        } catch (IOException e) {
            return new ResponseEntity<>("Failed to delete image from Cloudinary", HttpStatus.INTERNAL_SERVER_ERROR);
        }
        imageService.delete(id);
        return new ResponseEntity<>("image supprimée !", HttpStatus.OK);
    }*/




    /* end cloudinary*/

   /* @PostMapping("/addImage")
    public ResponseEntity<Image> addImage (@RequestBody Image image){
        Image carobj = imageRepository.save(image);
        return  new ResponseEntity<>(carobj, HttpStatus.OK);
    }
    @PostMapping("/updateImage/{imageID}")
    public ResponseEntity<Image> updateImage(@PathVariable Long imageID, @RequestBody Image image) {

        //covService.updateRide(ride);
        Optional<Image> oldImage=imageRepository.findById(imageID);
        if (oldImage.isPresent()){
            Image updatedImage=oldImage.get();
            updatedImage.setCar(image.getCar());
            updatedImage.setPath(image.getPath());
            updatedImage.setFormat(image.getFormat());
            updatedImage.setDateAdded(image.getDateAdded());


            Image carobj= imageRepository.save(updatedImage);
            return  new ResponseEntity<>(carobj, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/deleteImage/{imageID}")
    public ResponseEntity<HttpStatus> deleteImage(@PathVariable Long imageID) {
        imageRepository.deleteById(imageID);
        return  new ResponseEntity<>( HttpStatus.OK);
    }
    @GetMapping("getImageById/{imageID}")
    public ResponseEntity<Image> getImageById(@PathVariable Long imageID) {
        Optional<Image> R1=imageRepository.findById(imageID);
        return R1.map(image -> new ResponseEntity<>(image, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/getAllImage")
    public ResponseEntity <List<Image>> getAllImage() {
        try {
            List<Image> imageList = new ArrayList<>(imageRepository.findAll());

            if (imageList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(imageList, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
   /* @GetMapping("/images/{carId}")
    public ResponseEntity<List<Image>> getImagesByCarId(@PathVariable Long carId) {
        List<Image> images = icovService.getImagesByCarID(carId);
        if (!images.isEmpty()) {
            return ResponseEntity.ok(images);
        } else {
            return ResponseEntity.noContent().build();
        }
    }*/
   @GetMapping("/images/{carID}")
   public Image getImageByCarID(@PathVariable Long carID) {
       return icovService.getImageByCarID(carID);
   }
   @GetMapping("/getImagesByCarID/{carID}")
   public List<Image> getImagesByCarID(@PathVariable Long carID) {
       return icovService.getImagesByCarID(carID);
   }
    @GetMapping("/imagePath/{carID}")
    public String getImagePath(@PathVariable Long carID) {
        return icovService.getImagePath(carID);
    }
    /****************************** Security Controller ******************************/
    @PostMapping("/addSecurity")
    public ResponseEntity<Security> addSecurity(@RequestBody Security security) {
        Security addedSecurity = icovService.addSecurity(security);
        return new ResponseEntity<>(addedSecurity, HttpStatus.OK);
    }

    @PutMapping("/updateSecurity/{securityID}")
    public ResponseEntity<Security> updateSecurity(@PathVariable Long securityID, @RequestBody Security security) {
        Security updatedSecurity = icovService.updateSecurity(securityID, security);
        if (updatedSecurity != null) {
            return new ResponseEntity<>(updatedSecurity, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @DeleteMapping("/deleteSecurity/{securityID}")
    public ResponseEntity<HttpStatus> deleteSecurity(@PathVariable Long securityID) {
        icovService.deleteSecurity(securityID);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @GetMapping("/getSecurityById/{securityID}")
    public ResponseEntity<Security> getSecurityById(@PathVariable Long securityID) {
        Optional<Security> security = icovService.getSecurityById(securityID);
        return security.map(value -> new ResponseEntity<>(value, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    @GetMapping("/getAllSecurities")
    public ResponseEntity<List<Security>> getAllSecurities() {
        List<Security> securities = icovService.getAllSecurity();
        if (securities.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT);
        }
        return new ResponseEntity<>(securities, HttpStatus.OK);
    }

  /*  @PostMapping("/addSecurity")
    public ResponseEntity<Security> addSecurity (@RequestBody Security security){
        Security carobj = securityRepository.save(security);
        return  new ResponseEntity<>(carobj, HttpStatus.OK);
    }
    @PostMapping("/updateSecurity/{securityID}")
    public ResponseEntity<Security> updateSecurity(@PathVariable Long securityID, @RequestBody Security security) {

        //covService.updateRide(ride);
        Optional<Security> oldSecurity=securityRepository.findById(securityID);
        if (oldSecurity.isPresent()){
            Security updatedSecurity=oldSecurity.get();
            updatedSecurity.setReservation(security.getReservation());
            updatedSecurity.setIsActivated(security.getIsActivated());
            updatedSecurity.setSharedData(security.getSharedData());



            Security carobj= securityRepository.save(updatedSecurity);
            return  new ResponseEntity<>(carobj, HttpStatus.OK);

        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }
    @DeleteMapping("/deleteSecurity/{securityID}")
    public ResponseEntity<HttpStatus> deleteSecurity(@PathVariable Long securityID) {
        securityRepository.deleteById(securityID);
        return  new ResponseEntity<>( HttpStatus.OK);
    }
    @GetMapping("getSecurityById/{securityID}")
    public ResponseEntity<Security> getSecurityById(@PathVariable Long securityID) {
        Optional<Security> R1=securityRepository.findById(securityID);
        return R1.map(security -> new ResponseEntity<>(security, HttpStatus.OK)).orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }
    @GetMapping("/getAllSecurity")
    public ResponseEntity <List<Security>> getAllSecurity() {
        try {
            List<Security> securityList = new ArrayList<>(securityRepository.findAll());

            if (securityList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }

            return new ResponseEntity<>(securityList    , HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }*/
}