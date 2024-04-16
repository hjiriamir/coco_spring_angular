package tn.esprit.coco.controller;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.entity.Bus;
import tn.esprit.coco.entity.Stop;
import tn.esprit.coco.service.IBusServices;
import tn.esprit.coco.service.IStopServices;

import java.util.List;

@RestController
@RequestMapping("/Stop")
@CrossOrigin("*")
@RequiredArgsConstructor

public class StopController {
    private final IStopServices stopServices;
    @PostMapping("/add")
    public void addStop(@RequestBody Stop stop){
        stopServices.addStop(stop);
    }
    @PutMapping("/update")
    public Stop updateStop(@RequestBody Stop stop){
        return stopServices.updateStop(stop);
    }

    @GetMapping("/get/{id}")
    public Stop getStop(@PathVariable("id") Long idStop){
        return  stopServices.getStop(idStop);
    }
    @GetMapping("/get-all")
    public List<Stop> getAllStop(){
        return  stopServices.getAllStop();
    }
    @DeleteMapping("/remove/{id}")
    public void removeStop(@PathVariable("id") Long idStop){
       stopServices.removeStop(idStop);
    }
}
