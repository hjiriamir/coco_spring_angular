package tn.esprit.coco.controller;


import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.entity.Stop;
import tn.esprit.coco.entity.TripStop;
import tn.esprit.coco.service.ITripstopServices;

import java.util.List;

@RestController
@RequestMapping("/TripSTOP")
@CrossOrigin("*")
@RequiredArgsConstructor

public class TripStopController {

    private final ITripstopServices tripStopServices;

    @PostMapping("/{tripId}/{stopId}")
    public ResponseEntity<TripStop> addTripStop(@PathVariable Long tripId, @PathVariable Long stopId, @RequestBody TripStop newTripStop) {
        TripStop addedTripStop = tripStopServices.addTripStop(tripId, stopId, newTripStop);
        return ResponseEntity.ok(addedTripStop);
    }
    @PutMapping("/update")
    public TripStop updateTripStop(@RequestBody TripStop tripStop){
        return tripStopServices.updateTripStop(tripStop);
    }

    @GetMapping("/get/{id}")
    public TripStop getTripStop(@PathVariable("id") Long idTripStop){
        return  tripStopServices.getTripStop(idTripStop);
    }
    @GetMapping("/getbyidtrip/{id}")
    public List<TripStop> getTripStopByidTrip(@PathVariable("id") Long idTrip){
        return  tripStopServices.getTripStopByidTrip(idTrip);
    }
    @GetMapping("/getbyidtripstop/{id}")
    public Stop getStopByTripStop(@PathVariable("id") Long idTripStop){
        return  tripStopServices.getStopByTripStop(idTripStop);
    }
    @GetMapping("/get-all")
    public List<TripStop> getAllTripStop(){
        return  tripStopServices.getAllTripStop();
    }
    @DeleteMapping("/remove/{id}")
    public void removeTripStop(@PathVariable("id") Long idTripStop){
        tripStopServices.removeTripStop(idTripStop);
    }

    @GetMapping("/tri")
    List<TripStop> tri() {
        return tripStopServices.tri();
    }

}
