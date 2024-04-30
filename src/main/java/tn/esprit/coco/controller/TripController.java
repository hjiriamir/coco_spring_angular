package tn.esprit.coco.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.entity.Stop;
import tn.esprit.coco.entity.Trip;
import tn.esprit.coco.service.IStopServices;
import tn.esprit.coco.service.ITripServices;

import java.util.List;

@RestController
@RequestMapping("/Trip")
@CrossOrigin("*")
@RequiredArgsConstructor
public class TripController {
    private final ITripServices tripServices;
    @PostMapping("/add")
    public void addTrip(@RequestBody Trip trip){
        tripServices.addTrip(trip);
    }
    @PutMapping("/update")
    public Trip updateTrip(@RequestBody Trip trip){
        return tripServices.updateTrip(trip);
    }

    @GetMapping("/get/{id}")
    public Trip getTrip(@PathVariable("id") Long idTrip){
        return  tripServices.getTrip(idTrip);
    }
    @GetMapping("/get-all")
    public List<Trip> getAllTrip(){
        return  tripServices.getAllTrip();
    }
    @DeleteMapping("/remove/{id}")
    public void removeTrip(@PathVariable("id") Long idTrip){
        tripServices.removeTrip(idTrip);
    }
}
