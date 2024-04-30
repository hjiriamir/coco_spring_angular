package tn.esprit.coco.serviceImp;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.ObjectUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.coco.entity.Stop;
import tn.esprit.coco.entity.Trip;
import tn.esprit.coco.repository.StopRepository;
import tn.esprit.coco.repository.TripRepository;
import tn.esprit.coco.service.ITripServices;

import java.util.List;
@RequiredArgsConstructor
@Service
@Slf4j

public class TripServicesImpl implements ITripServices {
    @Autowired
    private TripRepository tripRepository;
    @Override
    public void addTrip(Trip trip) {
        tripRepository.save(trip);

    }

    @Override
    public Trip updateTrip(Trip trip) {
        return tripRepository.save(trip);
    }

    @Override
    public Trip getTrip(Long idTrip) {
        return tripRepository.findById(idTrip).orElse(null);
    }

    @Override
    public List<Trip> getAllTrip() {
        return (List<Trip>) tripRepository.findAll();
    }

    @Override
    public void removeTrip(Long idTrip) {
        tripRepository.deleteById(idTrip);
    }
}
