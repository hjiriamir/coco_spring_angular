package tn.esprit.coco.serviceImp;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.coco.entity.Bus;
import tn.esprit.coco.entity.Trip;
import tn.esprit.coco.repository.BusRepository;
import tn.esprit.coco.repository.TripRepository;
import tn.esprit.coco.service.IBusServices;

import java.util.List;
import java.util.Optional;


@Service
@Slf4j
public class BusServicesImpl implements IBusServices {
    private final BusRepository busRepository;
    private final TripRepository tripRepository;

    // Constructor Injection
    public BusServicesImpl(BusRepository busRepository, TripRepository tripRepository) {
        this.busRepository = busRepository;
        this.tripRepository = tripRepository;
    }
    @Override
    public void addBus(Bus bus) {
        busRepository.save(bus);
    }

    @Override
    public Bus updateBus(Bus bus) {
        return  busRepository.save(bus);


    }

    @Override
    public Bus getBus(Long idBus) {
        return busRepository.findById(idBus).orElse(null);
    }

    @Override
    public List<Bus> getAllBus() {
        return (List<Bus>) busRepository.findAll();
    }

    @Override
    public void removeBus(Long idBus) {
        busRepository.deleteById(idBus);
    }
    @Override
    public void assignBusToTrip(Long busId, Long tripId) {
        Optional<Bus> optionalBus = busRepository.findById(busId);
        Optional<Trip> optionalTrip = tripRepository.findById(tripId);

        if (optionalBus.isPresent() && optionalTrip.isPresent()) {
            Bus bus = optionalBus.get();
            Trip trip = optionalTrip.get();

            trip.setAssignedBus(bus);
            tripRepository.save(trip);
        } else {
            log.error("Bus or Trip not found with provided IDs.");
        }
    }


}

