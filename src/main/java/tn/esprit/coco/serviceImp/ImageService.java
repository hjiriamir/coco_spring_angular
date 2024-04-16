package tn.esprit.coco.serviceImp;

import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.coco.entity.Imagee;
import tn.esprit.coco.repository.ImageeRepository;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class ImageService {

    @Autowired
    ImageeRepository imageRepository;

    public List<Imagee> list(){
        return imageRepository.findByOrderById();
    }

    public Optional<Imagee> getOne(int id){
        return imageRepository.findById(id);
    }

    public void save(Imagee image){
        imageRepository.save(image);
    }

    public void delete(int id){
        imageRepository.deleteById(id);
    }

    public boolean exists(int id){
        return imageRepository.existsById(id);
    }
}
