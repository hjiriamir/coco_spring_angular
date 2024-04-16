package tn.esprit.coco.serviceImp;

import jakarta.validation.constraints.Null;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.coco.entity.Bus;
import tn.esprit.coco.entity.Stop;
import tn.esprit.coco.repository.BusRepository;
import tn.esprit.coco.repository.StopRepository;
import tn.esprit.coco.service.IStopServices;

import java.util.List;

@RequiredArgsConstructor
@Service
@Slf4j

public class StopServicesImpl implements IStopServices {
    @Autowired
    private StopRepository stopRepository;

    @Override
    public void addStop(Stop stop) {
        stopRepository.save(stop);
    }

    @Override
    public Stop updateStop(Stop stop) {
        return stopRepository.save(stop);
    }

    @Override
    public Stop getStop(Long idStop) {
        return stopRepository.findById(idStop).orElse(null);
    }

    @Override
    public List<Stop> getAllStop() {
        return (List<Stop>) stopRepository.findAll();
    }

    @Override
    public void removeStop(Long idStop) {
        stopRepository.deleteById(idStop);
    }
}
