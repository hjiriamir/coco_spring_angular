package tn.esprit.coco.service;

import tn.esprit.coco.entity.Bus;

import java.util.List;

public interface IBusServices {
    void addBus(Bus bus);
    Bus updateBus (Bus bus);
    Bus getBus(Long idBus);
    List<Bus> getAllBus();
    void removeBus(Long idBus);
}
