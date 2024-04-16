package tn.esprit.coco.service;

import tn.esprit.coco.entity.Bus;
import tn.esprit.coco.entity.Stop;

import java.util.List;

public interface IStopServices {
    void addStop(Stop stop);
    Stop updateStop (Stop stop);
    Stop getStop(Long idStop);
    List<Stop> getAllStop();
    void removeStop(Long idStop);
}
