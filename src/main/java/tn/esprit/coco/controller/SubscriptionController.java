package tn.esprit.coco.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import tn.esprit.coco.entity.Bus;
import tn.esprit.coco.entity.Subscription;
import tn.esprit.coco.entity.SubscriptionStatus;
import tn.esprit.coco.entity.Trip;
import tn.esprit.coco.serviceImp.SubscriptionServicesImpl;

import java.util.List;

@RestController
@CrossOrigin("*")
@RequestMapping("/subscriptions")

public class SubscriptionController {
    @Autowired
    private SubscriptionServicesImpl subscriptionService;

    @PostMapping("/{userId}/add")
    public ResponseEntity<?> addSubscription(@PathVariable Long userId, @RequestBody Subscription subscription) {
        Subscription addedSubscription = subscriptionService.addSubscription(userId, subscription);
        if (addedSubscription != null) {
            return ResponseEntity.ok(addedSubscription);
        } else {
            return ResponseEntity.badRequest().body("Failed to add subscription.");
        }
    }
    @GetMapping("/get-all")
    public List<Subscription> getAllSubscription(){
        return  subscriptionService.getAllSubscription();
    }
    @PutMapping("/{subscriptionId}/updateStatus/{newStatus}")
    public Subscription updateSubscriptionStatus(@PathVariable Long subscriptionId, @PathVariable SubscriptionStatus newStatus) {

        Subscription updatedSubscription = subscriptionService.updateStatus(subscriptionId, newStatus);

        return updatedSubscription;
    }
    @GetMapping("/get/{id}")
    public Subscription getSubscription(@PathVariable("id") Long iduser){
        return  subscriptionService.getSubscription(iduser);
    }



    @PutMapping("/{subscriptionId}/updateremainingTrips/{newremainingTrips}")
    public Subscription  updateremainingTrips(@PathVariable Long subscriptionId, @PathVariable int newremainingTrips) {

        Subscription updatedSubscription = subscriptionService.updateremainingTrips(subscriptionId, newremainingTrips);

        return updatedSubscription;
    }
    @DeleteMapping("/remove/{id}")
    public void removeSub(@PathVariable("id") Long idSub){
        subscriptionService.removeSub(idSub);
    }

}
