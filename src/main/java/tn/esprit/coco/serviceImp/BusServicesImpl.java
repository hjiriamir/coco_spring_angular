package tn.esprit.coco.serviceImp;

import jakarta.validation.constraints.Null;
import org.springframework.beans.factory.annotation.Autowired;
import tn.esprit.coco.entity.*;
import  tn.esprit.coco.repository.*;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import tn.esprit.coco.service.IBusServices;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j
public class BusServicesImpl implements IBusServices {
    @Autowired
    private BusRepository busRepository;
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
}
