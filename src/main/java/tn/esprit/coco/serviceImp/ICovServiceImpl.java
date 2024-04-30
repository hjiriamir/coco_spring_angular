package tn.esprit.coco.serviceImp;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import tn.esprit.coco.entity.*;
import tn.esprit.coco.repository.*;
import tn.esprit.coco.service.ICovService;

import java.util.*;

@Component
@Service
@Slf4j
@AllArgsConstructor
public class ICovServiceImpl implements ICovService {
    @Autowired
    private CarRepository carRepository;
    @Autowired
    private RideRepository rideRepository;
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private PaymentRepository paymentRepository;
    @Autowired
    private FavoriteRepository favoriteRepository;
    @Autowired
    private ImageRepository imageRepository;
    @Autowired
    private SecurityRepository securityRepository;
    @Autowired
    private UserRepository userRepository;
    @Override
    public Ride addRide(Ride ride) {
        return rideRepository.save(ride);
    }
   /* @Override
    public Ride updateRide(Long rideID, Ride updatedRide) {
        Optional<Ride> optionalRide = rideRepository.findById(rideID);
        if (optionalRide.isPresent()) {
            Ride oldRide = optionalRide.get();
            oldRide.setDriver(updatedRide.getDriver());
            oldRide.setPrice(updatedRide.getPrice());
            oldRide.setTime(updatedRide.getTime());
            oldRide.setDepartureDate(updatedRide.getDepartureDate());
            oldRide.setStartLocation(updatedRide.getStartLocation());
            oldRide.setPlaceDisponible(updatedRide.getPlaceDisponible());
            oldRide.setAvoidTolls(updatedRide.isAvoidTolls());
            oldRide.setPreference(updatedRide.getPreference());
            oldRide.setSmoking_Vehicle(updatedRide.isSmoking_Vehicle());
            return rideRepository.save(oldRide);
        }
        return null;
    }*/
   @Override
   public Ride updateRide(Long rideID, Ride updatedRide) {
       Optional<Ride> optionalRide = rideRepository.findById(rideID);
       if (optionalRide.isPresent()) {
           Ride oldRide = optionalRide.get();

           // Vérifier et mettre à jour uniquement les champs modifiés
           if (updatedRide.getDriver() != null) {
               oldRide.setDriver(updatedRide.getDriver());
           }
           if (updatedRide.getPrice() != 0) {
               oldRide.setPrice(updatedRide.getPrice());
           }
           if (updatedRide.getTime() != null) {
               oldRide.setTime(updatedRide.getTime());
           }
           if (updatedRide.getDepartureDate() != null) {
               oldRide.setDepartureDate(updatedRide.getDepartureDate());
           }
           if (updatedRide.getStartLocation() != null) {
               oldRide.setStartLocation(updatedRide.getStartLocation());
           }
           if (updatedRide.getPlaceDisponible() != 0) {
               oldRide.setPlaceDisponible(updatedRide.getPlaceDisponible());
           }
           if (updatedRide.isAvoidTolls()) {
               oldRide.setAvoidTolls(true);
           }
           if (updatedRide.getPreference() != null) {
               oldRide.setPreference(updatedRide.getPreference());
           }
           if (updatedRide.isSmoking_Vehicle()) {
               oldRide.setSmoking_Vehicle(true);
           }

           return rideRepository.save(oldRide);
       }
       return null;
   }
    @Override
     public void deleteRide(Long rideID) {
          rideRepository.deleteById(rideID);
        //return ResponseEntity.ok(HttpStatus.OK);
    }

    @Override
    public Optional<Ride> getRideById(Long rideID) {
        return rideRepository.findById(rideID);
    }

    @Override
    public List<Ride> getRideByStartlocation(String startlocation) {
        List<Ride> allRides = rideRepository.findAll();
        List<Ride> matchingRides = new ArrayList<>();
        for (Ride ride : allRides) {
            if (ride.getStartLocation().toLowerCase().contains(startlocation.toLowerCase())) {
                matchingRides.add(ride);
            }
        }
        return matchingRides;
    }
   /* @Override
    public ResponseEntity<Ride> getRideById(Long rideID) {
        Optional<Ride> rideOptional = rideRepository.findById(rideID);
        return rideOptional.map(ride -> new ResponseEntity<>(ride, HttpStatus.OK))
                .orElseGet(() -> new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }*/

    @Override
    public List<Ride> getAllRide() {
        return rideRepository.findAll();
    }

    @Override
    public List<String> getAddress() {
        List<Ride>rides=rideRepository.findAll();
        List<String>ListAdress=new ArrayList<>();
        for (Ride R1:rides){
            ListAdress.add(R1.getStartLocation());
        }
        return ListAdress;
    }
  /* @Override
    public List<Ride >getAllRide() {*/
        /*try {
            List<Ride> rideList = new ArrayList<>(rideRepository.findAll());

            if (rideList.isEmpty()) {
                return new ResponseEntity<>(HttpStatus.NO_CONTENT);
            }
                System.out.println("rides");
            return new ResponseEntity<>(rideList, HttpStatus.OK);
        } catch(Exception ex) {
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }*/
      /* List<Ride> rideList =new ArrayList<>();
       rideList= rideRepository.findAll();
       return rideList;
    }*/

   /* @Override
    public List<Ride> findAllRides() {
        return new ArrayList<>(rideRepository.findAll());
    }*/
 /*  @Override
    public List<Image> getImagesByCarID(Long carID) {
        Optional<Car> optionalCar = carRepository.findById(carID);
        List<Image> images = new ArrayList<>();

        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            images = car.getImages();
            for (Image image : images) {
                System.out.println("Format de l'image : " + image.getFormat());
            }
        }
        System.out.println("Format de l'image : ");
        return images;
    }*/
    /**************************** Car ImplService****************************/
    @Override
    public Car addCar(Car car) {
        return carRepository.save(car);
    }
    @Override
    public Optional<Car> findCarById(Long carId) {
        return carRepository.findById(carId);
    }

    @Override
    public Car getCarByRideID(Long rideID) {
        Optional<Ride> R1=rideRepository.findById(rideID);
        List<Car> cars = carRepository.findAll();
        if (R1.isPresent()) {
            Ride ride = R1.get();
            for (Car car : cars) {
                if (car.getRide().equals(ride)) {
                    return car;
                }
            }
        }
        return null;
    }

    @Override
    public Car getCarByModel(String model) {
        List<Car>cars=carRepository.findAll();
        for (Car c1:cars){
            if (c1.getModel().equals(model)){
                return c1;
            }
        }
        return null;
    }


    @Override
    public List<Car> findAllCars() {
        return new ArrayList<>(carRepository.findAll());
    }

   /* @Override
    public Car updateCar(Long carID, Car car) {
        return null;
    }*/

   /* @Override
     public Car updateCar(Long carID, Car car) {
         Optional<Car> oldCar = carRepository.findById(carID);
         if (oldCar.isPresent()) {
             Car updatedCar = oldCar.get();
             updatedCar.setComfort(car.getComfort());
             updatedCar.setRide(car.getRide());
             updatedCar.setModel(car.getModel());
             updatedCar.setImages(car.getImages());

             return carRepository.save(updatedCar);
         }else{

         throw new RuntimeException("Car not found");}
     }*/
   @Override
   public Car updateCar(Long carID, Car car) {
       Optional<Car> oldCar = carRepository.findById(carID);
       if (oldCar.isPresent()) {
           Car updatedCar = oldCar.get();
           updatedCar.setComfort(car.getComfort());
           updatedCar.setRide(car.getRide());
           updatedCar.setModel(car.getModel());
          // updatedCar.setImages(car.getImages()); // il faut l'ajouter apres


           return carRepository.save(updatedCar);
       }
       return null; // ou lever une exception
   }


    @Override
    public void deleteCar(Long carId) {
        carRepository.deleteById(carId);
    }

    @Override
    public Optional<Car> editCar(Long carID, Car car) {
        Optional<Car> optionalCar = carRepository.findById(carID);
        if (optionalCar.isPresent()) {
            Car existingCar = optionalCar.get();
            existingCar.setComfort(car.getComfort());
            existingCar.setRide(car.getRide());
            existingCar.setModel(car.getModel());
            existingCar.setImages(car.getImages());
            Car updatedCar = carRepository.save(existingCar);
            return Optional.of(updatedCar);
        } else {
            // Gérer le cas où la voiture avec l'ID spécifié n'est pas trouvée
            return Optional.empty();
        }
    }

    @Override
    public void affectCarToRide(Long carID, Long rideID) {
        Optional<Car> C1=carRepository.findById(carID);
        Optional<Ride> R1=rideRepository.findById(rideID);
        if (C1.isPresent() && R1.isPresent()){
            Car car=C1.get();
            Ride ride=R1.get();
            C1.get().setRide(ride);
            carRepository.save(car);
        }

    }



    /**************************** Reservation IService****************************/
   /* public Reservation addReservation(Reservation reservation) {
        return reservationRepository.save(reservation);
    }*/

    public void addReservation(Long passengerId, Long rideId) {
        Optional<User> optionalPassenger = userRepository.findById(passengerId);
        Optional<Ride> optionalRide = rideRepository.findById(rideId);

        if (optionalPassenger.isPresent() && optionalRide.isPresent()) {
            User passenger = optionalPassenger.get();
            Ride ride = optionalRide.get();

            // Créer une nouvelle réservation
            Reservation reservation = new Reservation();
            reservation.setPassenger(passenger);
            reservation.setRideR(ride);
            reservation.setReservationDate(new Date()); // Date de réservation actuelle

            // Enregistrer la réservation en base de données
            reservationRepository.save(reservation);
        } else {
            // Gérer le cas où l'utilisateur ou le trajet n'existe pas
            throw new IllegalArgumentException("Passenger or ride not found");
        }
    }
    @Override
    public Reservation updateReservation(Long reservationID, Reservation reservation) {
        Optional<Reservation> oldReservation = reservationRepository.findById(reservationID);
        if (oldReservation.isPresent()) {
            Reservation updatedReservation = oldReservation.get();
            updatedReservation.setReservationDate(reservation.getReservationDate());
            updatedReservation.setPassenger(reservation.getPassenger());
            updatedReservation.setPayment(reservation.getPayment());
            updatedReservation.setSecurity(reservation.getSecurity());
            updatedReservation.setRideR(reservation.getRideR());
            updatedReservation.setPassengerID(reservation.getPassengerID());


            return reservationRepository.save(updatedReservation);
        }
        return null; // or throw exception
    }
    @Override
    public void deleteReservation(Long reservationID) {
        reservationRepository.deleteById(reservationID);
    }
    @Override
    public Optional<Reservation> getReservationById(Long reservationID) {
        return reservationRepository.findById(reservationID);
    }
    @Override
    public List<Reservation> getAllReservation() {
        return reservationRepository.findAll();
    }



    /**************************** Payment IService****************************/
    @Override
    public Payment addPayment(Payment payment) {
        return paymentRepository.save(payment);
    }
    @Override
    public Payment updatePayment(Long paymentID, Payment payment) {
        Optional<Payment> oldPayment = paymentRepository.findById(paymentID);
        if (oldPayment.isPresent()) {
            Payment updatedPayment = oldPayment.get();
            updatedPayment.setTypePayment(payment.getTypePayment());
            updatedPayment.setPaymentStatus(payment.getPaymentStatus());
            updatedPayment.setAmount(payment.getAmount());
            updatedPayment.setReservation(payment.getReservation());



            return paymentRepository.save(updatedPayment);
        }
        return null; // or throw exception
    }
    @Override
    public void deletePayment(Long paymentID) {
        paymentRepository.deleteById(paymentID);
    }
    @Override
    public Optional<Payment> getPaymentById(Long paymentID) {
        return paymentRepository.findById(paymentID);
    }
    @Override
    public List<Payment> getAllPayment() {
        return paymentRepository.findAll();
    }
    /**************************** Favorite IService****************************/
    @Override
    public void addFavorite(Long passengerId, Long rideId) {
        //return favoriteRepository.save(favorite);
        Optional<User> passager=userRepository.findById(passengerId);
        Optional<Ride> ride=rideRepository.findById(rideId);
        Favorite favorite = new Favorite();
        if (passager.isPresent() && ride.isPresent()){
            User P1=passager.get();
            Ride R1=ride.get();
            favorite.setRide(R1);
            favorite.setUser(P1);
            favoriteRepository.save(favorite);
        }

    }

    @Override
    public Favorite updateFavorite(Long favoriteID, Favorite favorite) {
        Optional<Favorite> oldFavorite = favoriteRepository.findById(favoriteID);
        if (oldFavorite.isPresent()) {
            Favorite updatedFavorite = oldFavorite.get();
            updatedFavorite.setUser(favorite.getUser());
            updatedFavorite.setRide(favorite.getRide());


            return favoriteRepository.save(updatedFavorite);
        }
        return null; // ou lever une exception
    }

    @Override
    public void deleteFavorite(Long favoriteID) {
        favoriteRepository.deleteById(favoriteID);
    }

    @Override
    public Optional<Favorite> getFavoriteById(Long favoriteID) {
        return favoriteRepository.findById(favoriteID);
    }

    @Override
    public List<Favorite> getAllFavorite() {
        return favoriteRepository.findAll();
    }

    @Override
    public List<Favorite> getFavoritesByPassenger(Long userID) {
        Optional<User> passenger=userRepository.findById(userID);
        List<Favorite>MyFavorites=new ArrayList<>();
        if (passenger.isPresent()){
            MyFavorites=passenger.get().getFavorites();
            return MyFavorites;
        }
        return Collections.emptyList();
    }

    /**************************** Image IService****************************/
    @Override
    public Image addImage(Image image) {
        return imageRepository.save(image);
    }

    @Override
    public String getImagePath(Long carID) {

        return getImageByCarID(carID).getPath();
    }

    @Override
    public Image updateImage(Long imageID, Image image) {
        Optional<Image> oldImage = imageRepository.findById(imageID);
        if (oldImage.isPresent()) {
            Image updatedImage = oldImage.get();
            updatedImage.setPath(image.getPath());
            updatedImage.setCar(image.getCar());
            updatedImage.setFormat(image.getFormat());
            updatedImage.setDateAdded(image.getDateAdded());
            updatedImage.setTime(image.getTime());
            updatedImage.setDescription(image.getDescription());


            return imageRepository.save(updatedImage);
        }
        return null; // ou lever une exception
    }

    @Override
    public void deleteImage(Long imageID) {
        imageRepository.deleteById(imageID);
    }

    @Override
    public Optional<Image> getImageById(Long imageID) {
        return imageRepository.findById(imageID);
    }

    @Override
    public List<Image> getAllImage() {
        return imageRepository.findAll();
    }

    @Override
    public void affectImageToCar(Long imageID, Long carID) {
        Optional<Image> Im1=imageRepository.findById(imageID);
        Optional<Car> C=carRepository.findById(carID);
        if (Im1.isPresent() && C.isPresent()){
            Car car=C.get();
            Image image=Im1.get();
            image.setCar(car);
            imageRepository.save(image);
        }
    }

    @Override
    public List<Image> getImagesByCarID(Long carID) {
        List<Image> images = imageRepository.findAll();
        Optional<Car> optionalCar = carRepository.findById(carID);
        List<Image> imageList = new ArrayList<>();
        if (optionalCar.isPresent()) {
            Car car = optionalCar.get();
            for (Image image : images) {
                if (image.getCar().equals(car)) {
                    imageList.add(image);
                }
            }
        }
        return imageList;
    }

    @Override
    public Image getImageByCarID(Long carID) {
        Optional<Car> R1=carRepository.findById(carID);
        List<Image> images = imageRepository.findAll();
        if (R1.isPresent()) {
            Car car = R1.get();
            for (Image image : images) {
                if (image.getCar().equals(car)) {
                    return image;
                }
            }
        }
        return null;
    }


    /**************************** Security IService****************************/
    @Override
    public Security addSecurity(Security security) {
        return securityRepository.save(security);
    }

    @Override
    public Security updateSecurity(Long securityID, Security security) {
        Optional<Security> oldSecurity = securityRepository.findById(securityID);
        if (oldSecurity.isPresent()) {
            Security updatedSecurity = oldSecurity.get();
            updatedSecurity.setSharedData(security.getSharedData());
            updatedSecurity.setIsActivated(security.getIsActivated());
            updatedSecurity.setReservation(security.getReservation());

            return securityRepository.save(updatedSecurity);
        }
        return null; // ou lever une exception
    }

    @Override
    public void deleteSecurity(Long securityID) {
        securityRepository.deleteById(securityID);
    }

    @Override
    public Optional<Security> getSecurityById(Long securityID) {
        return securityRepository.findById(securityID);
    }

    @Override
    public List<Security> getAllSecurity() {
        return securityRepository.findAll();
    }


}
