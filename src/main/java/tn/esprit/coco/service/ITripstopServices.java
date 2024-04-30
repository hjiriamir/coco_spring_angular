package tn.esprit.coco.service;

import tn.esprit.coco.entity.Trip;
import tn.esprit.coco.entity.TripStop;

import java.util.List;

public interface ITripstopServices {
     TripStop addTripStop(Long tripId, Long stopId, TripStop newTripStop);
     TripStop updateTripStop (TripStop tripStop);
     TripStop getTripStop(Long idTripStop);
     List<TripStop> getAllTripStop();
     void removeTripStop(Long idTripStop);
     public List<TripStop> tri();


}
