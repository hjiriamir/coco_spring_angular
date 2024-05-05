package tn.esprit.coco.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.entity.Bus;
import tn.esprit.coco.service.IBusServices;

import java.util.List;

@RestController
@RequestMapping("/Bus")
@CrossOrigin("*")
@RequiredArgsConstructor
public class BusController {
    private final IBusServices busServices;
    @PostMapping("/add")
    public void addBus(@RequestBody Bus bus){
      busServices.addBus(bus);
    }
    @CrossOrigin(origins = "*")
    @PutMapping("/update")
    public Bus  updateBus(@RequestBody Bus bus){
        return busServices.updateBus(bus);
    }
    @GetMapping("/get/{id}")
    public Bus getBus(@PathVariable("id") Long idBus){
        return  busServices.getBus(idBus);
    }
    @GetMapping("/get-all")
    public List<Bus> getAllBus(){
        return  busServices.getAllBus();
    }
    @DeleteMapping("/remove/{id}")
    public void removeBus(@PathVariable("id") Long idBus){
        busServices.removeBus(idBus);
    }
    @PostMapping("/assign-bus-to-trip")
    public ResponseEntity<String> assignBusToTrip(@RequestParam("busId") Long busId, @RequestParam("tripId") Long tripId) {
        try {
            busServices.assignBusToTrip(busId, tripId);
            return ResponseEntity.ok("Bus successfully assigned to trip.");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to assign bus to trip: " + e.getMessage());
        }
    }
}
