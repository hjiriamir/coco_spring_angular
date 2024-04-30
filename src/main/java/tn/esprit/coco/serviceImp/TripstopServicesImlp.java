package tn.esprit.coco.serviceImp;

import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.coco.entity.Stop;
import tn.esprit.coco.entity.Trip;
import tn.esprit.coco.entity.TripStop;
import tn.esprit.coco.repository.StopRepository;
import tn.esprit.coco.repository.TripRepository;
import tn.esprit.coco.repository.TripStopRepository;
import tn.esprit.coco.service.ITripServices;
import tn.esprit.coco.service.ITripstopServices;

import java.util.List;
import java.util.Optional;

@RequiredArgsConstructor
@Service
@Slf4j

public class TripstopServicesImlp implements ITripstopServices {

    private final TripStopRepository tripStopRepository;
    private final TripRepository tripRepository;
    private final StopRepository stopRepository;
    @Override
    public TripStop addTripStop(Long tripId, Long stopId, TripStop newTripStop) {
        Optional<Trip> tripOptional = tripRepository.findById(tripId);
        Optional<Stop> stopOptional = stopRepository.findById(stopId);

        if (tripOptional.isPresent() && stopOptional.isPresent()) {
            Trip trip = tripOptional.get();
            Stop stop = stopOptional.get();


            newTripStop.setTrip(trip);
            newTripStop.setStop(stop);


            return tripStopRepository.save(newTripStop);
        } else {
            throw new EntityNotFoundException("Trip or Stop not found");
        }
    }

    /*@Override
    public TripStop updateTripStop(TripStop tripStop) {
        return tripStopRepository.save(tripStop);
    }*/
    @Override
    public TripStop updateTripStop(TripStop tripStop) {
        Optional<TripStop> existingTripStopOptional = tripStopRepository.findById(tripStop.getId());

        if (existingTripStopOptional.isPresent()) {
            TripStop existingTripStop = existingTripStopOptional.get();
            existingTripStop.setArrivalTime(tripStop.getArrivalTime());
            existingTripStop.setDepartureTime(tripStop.getDepartureTime());
            existingTripStop.setAmount(tripStop.getAmount());


            if (tripStop.getTrip() != null) {
                existingTripStop.setTrip(tripStop.getTrip());
            }
            if (tripStop.getStop() != null) {
                existingTripStop.setStop(tripStop.getStop());
            }

            return tripStopRepository.save(existingTripStop);
        } else {
            throw new EntityNotFoundException("TripStop not found");
        }
    }

    @Override
    public TripStop getTripStop(Long idTripStop) {
        return tripStopRepository.findById(idTripStop).orElse(null);
    }

    @Override
    public List<TripStop> getAllTripStop() {
        return (List<TripStop>) tripStopRepository.findAll();
    }

    @Override
    public void removeTripStop(Long idTripStop) {tripStopRepository.deleteById(idTripStop);

    }
    @Override
    public List<TripStop> tri() {
        return tripStopRepository.findAllByOrderByArrivalTime();
    }


}


