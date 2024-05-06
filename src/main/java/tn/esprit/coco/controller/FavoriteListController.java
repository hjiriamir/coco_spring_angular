package tn.esprit.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.entity.Accommodation;
import tn.esprit.coco.entity.FavoriteList;
import tn.esprit.coco.service.FavoriteListService;

import java.util.List;

@CrossOrigin(origins = "http://localhost:4200",methods={RequestMethod.POST,RequestMethod.PUT, RequestMethod.DELETE,RequestMethod.GET,RequestMethod.PATCH})
@RestController
@RequestMapping("/api/favorite-list")
public class FavoriteListController {

    private final FavoriteListService favoriteListService;

    @Autowired
    public FavoriteListController(FavoriteListService favoriteListService) {
        this.favoriteListService = favoriteListService;
    }
    @GetMapping("/{userId}/favorites")
    public ResponseEntity<List<Accommodation>> getFavoriteListAccomodationByUserId(@PathVariable Long userId) {
        List<Accommodation> accommodations = favoriteListService.getFavoriteListAccomodationByUserId(userId);
        return ResponseEntity.ok(accommodations);
    }
    @PostMapping("/create")
    public ResponseEntity<FavoriteList> createFavoriteList(@RequestBody FavoriteList favoriteList) {
        FavoriteList createdFavoriteList = favoriteListService.createFavoriteList(favoriteList);
        return new ResponseEntity<>(createdFavoriteList, HttpStatus.CREATED);
    }

    @GetMapping("getListById/{id}")
    public ResponseEntity<FavoriteList> getFavoriteListById(@PathVariable Long id) {
        FavoriteList favoriteList = favoriteListService.getFavoriteListById(id);
        return ResponseEntity.ok(favoriteList);
    }

    @GetMapping("/getAll")
    public ResponseEntity<List<FavoriteList>> getAllFavoriteLists() {
        List<FavoriteList> favoriteLists = favoriteListService.getAllFavoriteLists();
        return ResponseEntity.ok(favoriteLists);
    }

    @PutMapping("updateListe/{id}")
    public ResponseEntity<FavoriteList> updateFavoriteList(@PathVariable Long id, @RequestBody FavoriteList favoriteList) {
        FavoriteList updatedFavoriteList = favoriteListService.updateFavoriteList(id, favoriteList);
        return ResponseEntity.ok(updatedFavoriteList);
    }

    @DeleteMapping("deleteList/{id}")
    public ResponseEntity<Void> deleteFavoriteList(@PathVariable Long id) {
        favoriteListService.deleteFavoriteList(id);
        return ResponseEntity.noContent().build();
    }



    @PostMapping("/{userId}/favorites/{accommodationId}")
    public ResponseEntity<Void> addAccommodationToFavoriteList(@PathVariable Long userId, @PathVariable Long accommodationId) {
        favoriteListService.addAccommodationToFavoriteList(userId, accommodationId);
        return ResponseEntity.ok().build();
    }

}
