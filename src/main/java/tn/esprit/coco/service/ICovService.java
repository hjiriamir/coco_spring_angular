package tn.esprit.coco.service;

import tn.esprit.coco.entity.*;

import java.util.List;
import java.util.Optional;

public interface ICovService {

    //public List<Image> getImagesByCarID(Long carID);
    public Ride addRide(Ride ride);
    public Ride updateRide(Long rideID, Ride ride);
    public void deleteRide(Long rideID);
    //ResponseEntity<Ride> getRideById(Long rideID);
    public Optional<Ride> getRideById(Long rideID);
    public List<Ride> getRideByStartlocation(String startlocation);
    public List<Ride> getAllRide();

public List<String>getAddress();
    /**************************** Car IService****************************/
    public Car addCar(Car car);
    public Optional<Car> findCarById(Long carId);
    public Car getCarByRideID(Long rideID);
    public Car getCarByModel(String model);


    public List<Car> findAllCars();
    //public Car updateCar(Long carID, Car car);
    public void deleteCar(Long carId);
    //public Car updateVehicle(Long id, Car updatedCar);
    public Car updateCar(Long carID, Car car);
    Optional<Car> editCar(Long carID, Car car);
    public void affectCarToRide(Long carID,Long rideID);
/**************************** Reservation IService****************************/
public void addReservation(Long passengerId, Long rideId);
    public Reservation updateReservation(Long reservationID, Reservation reservation);
    public void deleteReservation(Long reservationID);
    public Optional<Reservation> getReservationById(Long reservationID);
    public List<Reservation> getAllReservation();
    /**************************** Payment IService****************************/
    public Payment addPayment(Payment payment);
    public Payment updatePayment(Long paymentID, Payment payment);
    public void deletePayment(Long paymentID);
    public Optional<Payment> getPaymentById(Long paymentID);
    public List<Payment> getAllPayment();
    /**************************** Favorite IService****************************/

   // public Favorite addFavorite(Favorite favorite);
    public void addFavorite(Long passengerId, Long rideId);
    public Favorite updateFavorite(Long favoriteID, Favorite favorite);
    public void deleteFavorite(Long favoriteID);
    public Optional<Favorite> getFavoriteById(Long favoriteID);
    public List<Favorite> getAllFavorite();
    public List<Favorite> getFavoritesByPassenger(Long userID);
    /**************************** Image IService****************************/
    public Image addImage(Image image);
    public String getImagePath(Long carID);
    public Image updateImage(Long imageID, Image image);
    public void deleteImage(Long imageID);
    public Optional<Image> getImageById(Long imageID);
    public List<Image> getAllImage();
    public void affectImageToCar(Long imageID,Long carID);
    public List<Image> getImagesByCarID(Long carID);
    public Image getImageByCarID(Long carID);
    /**************************** Security IService****************************/
    public Security addSecurity(Security security);
    public Security updateSecurity(Long securityID, Security security);
    public void deleteSecurity(Long securityID);
    public Optional<Security> getSecurityById(Long securityID);
    public List<Security> getAllSecurity();

}
