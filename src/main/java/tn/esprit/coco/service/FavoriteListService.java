package tn.esprit.coco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tn.esprit.coco.entity.Accommodation;
import tn.esprit.coco.entity.FavoriteList;
import tn.esprit.coco.entity.User;
import tn.esprit.coco.repository.AccommodationRepository;
import tn.esprit.coco.repository.FavoriteListRepository;
import tn.esprit.coco.repository.UserRepository;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class FavoriteListService {

    private final FavoriteListRepository favoriteListRepository;
    private final AccommodationRepository accommodationRepository;
    @Autowired
    private UserRepository userRepository;

    public List<Accommodation> getFavoriteListAccomodationByUserId(Long userId) {
        List<FavoriteList> favoriteLists = favoriteListRepository.findByUserId(userId);
        List<Accommodation> accommodations = favoriteLists.stream()
                .map(FavoriteList::getAccommodations)
                .flatMap(List::stream)
                .distinct()
                .collect(Collectors.toList());
        return accommodations;
    }

    public void addAccommodationToFavoriteList(Long userId, Long accommodationId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        Accommodation accommodation = accommodationRepository.findById(accommodationId)
                .orElseThrow(() -> new RuntimeException("Accommodation not found"));

        FavoriteList favoriteList = user.getFavoriteList();
        if (favoriteList == null) {

            FavoriteList favoriteList1 = new FavoriteList();
            favoriteList = new FavoriteList();
            favoriteList.setUser(user);
            favoriteList.setAccommodationID(accommodationId);
           favoriteListRepository.save(favoriteList); // Sauvegardez la nouvelle liste de favoris


        }

        favoriteList.getAccommodations().add(accommodation);
        accommodation.getFavoriteList().add(favoriteList);
        user.setFavoriteList(favoriteList);
        userRepository.save(user);
        accommodationRepository.save(accommodation);
    }


    @Autowired
    public FavoriteListService(FavoriteListRepository favoriteListRepository, AccommodationRepository accommodationRepository) {
        this.favoriteListRepository = favoriteListRepository;
        this.accommodationRepository = accommodationRepository;
    }
    public FavoriteList createFavoriteList(FavoriteList favoriteList) {
        return favoriteListRepository.save(favoriteList);
    }

    public FavoriteList getFavoriteListById(Long id) {
        return favoriteListRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("FavoriteList not found with id: " + id));
    }

    public List<FavoriteList> getAllFavoriteLists() {
        return favoriteListRepository.findAll();
    }

    public FavoriteList updateFavoriteList(Long id, FavoriteList favoriteList) {
        FavoriteList existingFavoriteList = getFavoriteListById(id);
        return favoriteListRepository.save(existingFavoriteList);
    }

    public void deleteFavoriteList(Long id) {
        favoriteListRepository.deleteById(id);
    }


}
