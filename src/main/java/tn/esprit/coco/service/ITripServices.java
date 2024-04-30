package tn.esprit.coco.service;

import tn.esprit.coco.entity.Stop;
import tn.esprit.coco.entity.Trip;

import java.util.List;

public interface ITripServices {
    void addTrip(Trip trip);
    Trip updateTrip (Trip trip);
    Trip getTrip(Long idTrip);
    List<Trip> getAllTrip();
    void removeTrip(Long idTrip);
}
