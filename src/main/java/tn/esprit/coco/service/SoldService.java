package tn.esprit.coco.service;

import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.coco.entity.Sold;
import tn.esprit.coco.entity.User;
import tn.esprit.coco.repository.UserRepository;

import java.util.Optional;

@Service
public class SoldService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private SoldRepository soldRepository;

    public Sold updateSoldForUser(Long userId, Long newAccountSoldValue) {
        Optional<Sold> soldOptional = soldRepository.findByUserId(userId);
        if (soldOptional.isPresent()) {
            Sold sold = soldOptional.get();
            sold.setAccountSold(newAccountSoldValue);
            return soldRepository.save(sold);
        } else {
            throw new EntityNotFoundException("Sold not found for user with ID: " + userId);
        }
    }
    public Optional<Sold> findCurrentUserSoldByIdUser(Long userId) {
        return soldRepository.findCurrentUserSoldByIdUser(userId);
    }
    public Sold createSoldForUser(Long userId, Long accountSoldValue) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new EntityNotFoundException("User not found"));
        Sold sold = new Sold();
        sold.setUser(user);
        sold.setAccountSold(accountSoldValue);
        return soldRepository.save(sold);
    }

}
